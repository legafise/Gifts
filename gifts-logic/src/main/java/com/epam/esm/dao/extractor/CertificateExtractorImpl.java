package com.epam.esm.dao.extractor;

import com.epam.esm.dao.mapper.TagMapperImpl;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Component
public class CertificateExtractorImpl implements ResultSetExtractor<List<Certificate>> {
    private static final String CERTIFICATE_ID = "certificate_id";
    private static final String TIME_ZONE = "UTC";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private final TagMapperImpl tagMapper;

    @Autowired
    public CertificateExtractorImpl(TagMapperImpl tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Certificate> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
        List<Certificate> certificateList = new ArrayList<>();
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_PATTERN); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);

        while (resultSet.next()) {
            Certificate certificate = new Certificate();
            certificate.setId(resultSet.getLong(CERTIFICATE_ID));
            certificate.setName(resultSet.getString("gift_certificate_name"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getBigDecimal("price"));
            certificate.setDuration(resultSet.getShort("duration"));
            certificate.setCreateDate(df.format(resultSet.getDate("create_date")));
            certificate.setLastUpdateDate(df.format(resultSet.getDate("last_update_date")));
            certificate.setTags(mapCertificateTags(resultSet));

            certificateList.add(certificate);
        }

        return certificateList;
    }


    private List<Tag> mapCertificateTags(ResultSet resultSet) throws SQLException {
        List<Tag> tagList = new ArrayList<>();
        long mappingCertificateId = resultSet.getLong(CERTIFICATE_ID);

        while (!resultSet.isAfterLast()) {
            if (resultSet.getLong(CERTIFICATE_ID) == mappingCertificateId) {
                tagList.add(tagMapper.mapRow(resultSet, 1));
            } else if (resultSet.getLong(CERTIFICATE_ID) != mappingCertificateId) {
                resultSet.previous();
                return tagListChecker(tagList);
            }

            resultSet.next();
        }

        return tagListChecker(tagList);
    }

    private List<Tag> tagListChecker(List<Tag> tagList) {
        return tagList.size() == 1 && tagList.get(0).getName() == null ? new ArrayList<>() : tagList;
    }
}
