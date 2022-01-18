package com.epam.esm.dao.impl;

import com.epam.esm.dao.*;
import com.epam.esm.entity.Certificate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificateDaoImpl implements CertificateDao {
    private static final String ADD_CERTIFICATE_SQL = "INSERT INTO gift_certificate (name, description, price," +
            " duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CERTIFICATE_SQL = "UPDATE gift_certificate SET name = ?, description = ?, price = ?," +
            " duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";
    private static final String REMOVE_CERTIFICATE_BY_ID_SQL = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String FIND_CERTIFICATE_BY_ID_SQL = "SELECT gift_certificate.id AS certificate_id," +
            " gift_certificate.name AS gift_certificate_name, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " tag_id, tag.name AS tag_name FROM gifts.gift_certificate LEFT JOIN gift_tags" +
            " ON gift_certificate.id = gift_tags.certificate_id LEFT JOIN tag ON gift_tags.tag_id = tag.id WHERE gift_certificate.id = ?";
    private static final String FIND_ALL_CERTIFICATES_SQL = "SELECT gift_certificate.id AS certificate_id," +
            " gift_certificate.name AS gift_certificate_name, gift_certificate.description, gift_certificate.price," +
            " gift_certificate.duration, gift_certificate.create_date, gift_certificate.last_update_date, tag.id AS" +
            " tag_id, tag.name AS tag_name FROM gifts.gift_certificate LEFT JOIN gift_tags" +
            " ON gift_certificate.id = gift_tags.certificate_id LEFT JOIN tag ON gift_tags.tag_id = tag.id ORDER BY gift_certificate.id";
    private final DaoMapper mapper;

    public CertificateDaoImpl() {
        mapper = new DaoMapper();
    }

    @Override
    public boolean add(Certificate certificate) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_CERTIFICATE_SQL)) {
            fillCertificateData(certificate, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_CERTIFICATE_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<Certificate> certificateOptional = Optional.empty();

            if (resultSet.next()) {
                certificateOptional = Optional.of(mapper.mapCertificate(resultSet));
            }

            return certificateOptional;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Certificate> findAll() {
        try(Connection connection = DataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_CERTIFICATES_SQL);
            List<Certificate> certificateList = new ArrayList<>();

            while (resultSet.next()) {
                certificateList.add(mapper.mapCertificate(resultSet));
            }

            return certificateList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Certificate certificate) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CERTIFICATE_SQL)) {
            fillCertificateData(certificate, statement);
            statement.setLong(7, certificate.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean remove(Long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_CERTIFICATE_BY_ID_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillCertificateData(Certificate certificate, PreparedStatement statement) {
        try {
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setLong(4, certificate.getDuration().toDays());
            statement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            statement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
