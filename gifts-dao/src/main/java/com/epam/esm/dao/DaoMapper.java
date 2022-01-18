package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class DaoMapper {
    private static final String CERTIFICATE_ID = "certificate_id";
    private static final String CERTIFICATE_NAME = "gift_certificate_name";
    private static final String INVALID_RESULT_SET_MESSAGE = "Invalid result set";

    public Tag mapTag(ResultSet resultSet) {
        try {
            Tag tag = new Tag();
            tag.setId(resultSet.getLong("tag_id"));
            tag.setName(resultSet.getString("tag_name"));
            return tag;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Certificate mapCertificate(ResultSet resultSet) {
        try {
            Certificate certificate = new Certificate();
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
            df.setTimeZone(tz);

            certificate.setId(resultSet.getLong(CERTIFICATE_ID));
            certificate.setName(resultSet.getString("gift_certificate_name"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getBigDecimal("price"));
            certificate.setDuration(Duration.ofDays(resultSet.getLong("duration")));
            certificate.setCreateDate(df.format(resultSet.getDate("create_date")));
            certificate.setLastUpdateDate(df.format(resultSet.getDate("last_update_date")));
            certificate.setTags(mapTags(resultSet));
            return certificate;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<Tag> mapTags(ResultSet resultSet) {
        try {
            List<Tag> tagList = new ArrayList<>();
            long mappingCertificateId = resultSet.getLong(CERTIFICATE_ID);

            while (!resultSet.isAfterLast()) {
                if (resultSet.getLong(CERTIFICATE_ID) == mappingCertificateId) {
                    tagList.add(mapTag(resultSet));
                } else if (resultSet.getLong(CERTIFICATE_ID) != mappingCertificateId) {
                    resultSet.previous();
                    return tagListChecker(tagList);
                }

                resultSet.next();
            }

            return tagListChecker(tagList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<Tag> tagListChecker(List<Tag> tagList) {
        return tagList.size() == 1 && tagList.get(0).getName() == null ? new ArrayList<>() : tagList;
    }
}
