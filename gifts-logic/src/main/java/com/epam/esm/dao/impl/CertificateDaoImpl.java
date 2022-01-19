package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.extractor.CertificateExtractorImpl;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
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
    private final CertificateExtractorImpl certificateExtractor;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDaoImpl(CertificateExtractorImpl certificateExtractor, JdbcTemplate jdbcTemplate) {
        this.certificateExtractor = certificateExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Certificate certificate) {
        return 1 == jdbcTemplate.update(ADD_CERTIFICATE_SQL, certificate.getName(), certificate.getDescription(),
                certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate());
    }

    @Override
    public Optional<Certificate> findById(long id) {
        List<Certificate> certificateList = jdbcTemplate.query(FIND_CERTIFICATE_BY_ID_SQL, certificateExtractor, id);
        return Objects.requireNonNull(certificateList).isEmpty() ? Optional.empty() : Optional.of(certificateList.get(0));
    }

    @Override
    public List<Certificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_CERTIFICATES_SQL, certificateExtractor);
    }

    @Override
    public boolean update(Certificate certificate) {
        return 1 <= jdbcTemplate.update(UPDATE_CERTIFICATE_SQL, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), certificate.getId());
    }

    @Override
    public boolean remove(long id) {
        return 1 == jdbcTemplate.update(REMOVE_CERTIFICATE_BY_ID_SQL, id);
    }
}