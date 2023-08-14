package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Profile({"template", "template-test"})
public class MJCCertificateMapperImpl implements RowMapper<Certificate> {
    private static final String CERTIFICATE_ID = "certificate_id";
    private static final String GIFT_CERTIFICATE_NAME = "gift_certificate_name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "certificate_price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";
    private static final String IS_DELETED = "is_deleted";

    @Override
    public Certificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(resultSet.getLong(CERTIFICATE_ID));
        certificate.setName(resultSet.getString(GIFT_CERTIFICATE_NAME));
        certificate.setDescription(resultSet.getString(DESCRIPTION));
        certificate.setPrice(resultSet.getBigDecimal(PRICE));
        certificate.setDuration(resultSet.getShort(DURATION));
        certificate.setCreateDate(resultSet.getTimestamp(CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(resultSet.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        certificate.setDeleted(resultSet.getBoolean(IS_DELETED));

        return certificate;
    }
}
