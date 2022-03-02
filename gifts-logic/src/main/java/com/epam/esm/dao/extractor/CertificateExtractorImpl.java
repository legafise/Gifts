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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

//@Component
//public class CertificateExtractorImpl implements ResultSetExtractor<List<Certificate>> {
//    private static final String CERTIFICATE_ID = "certificate_id";
//    private static final String GIFT_CERTIFICATE_NAME = "gift_certificate_name";
//    private static final String DESCRIPTION = "description";
//    private static final String PRICE = "price";
//    private static final String DURATION = "duration";
//    private static final String CREATE_DATE = "create_date";
//    private static final String LAST_UPDATE_DATE = "last_update_date";
//    private final TagMapperImpl tagMapper;
//
//    @Autowired
//    public CertificateExtractorImpl(TagMapperImpl tagMapper) {
//        this.tagMapper = tagMapper;
//    }
//
//    @Override
//    public List<Certificate> extractData(ResultSet resultSet) throws DataAccessException, SQLException {
//        List<Certificate> certificateList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            Certificate certificate = new Certificate();
//            certificate.setId(resultSet.getLong(CERTIFICATE_ID));
//            certificate.setName(resultSet.getString(GIFT_CERTIFICATE_NAME));
//            certificate.setDescription(resultSet.getString(DESCRIPTION));
//            certificate.setPrice(resultSet.getBigDecimal(PRICE));
//            certificate.setDuration(resultSet.getShort(DURATION));
//            certificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
//            certificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
//            certificate.setTags(mapCertificateTags(resultSet));
//
//            certificateList.add(certificate);
//        }
//
//        return certificateList;
//    }
//
//
//    private List<Tag> mapCertificateTags(ResultSet resultSet) throws SQLException {
//        List<Tag> tagList = new ArrayList<>();
//        long mappingCertificateId = resultSet.getLong(CERTIFICATE_ID);
//
//        while (!resultSet.isAfterLast()) {
//            if (resultSet.getLong(CERTIFICATE_ID) == mappingCertificateId) {
//                tagList.add(tagMapper.mapRow(resultSet, 1));
//            } else if (resultSet.getLong(CERTIFICATE_ID) != mappingCertificateId) {
//                resultSet.previous();
//                return tagListChecker(tagList);
//            }
//
//            resultSet.next();
//        }
//
//        return tagListChecker(tagList);
//    }
//
//    private List<Tag> tagListChecker(List<Tag> tagList) {
//        return tagList.size() == 1 && tagList.get(0).getName() == null ? new ArrayList<>() : tagList;
//    }
//}
