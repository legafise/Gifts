package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CertificateDao {
    boolean add(Certificate certificate) throws ParseException;

    Optional<Certificate> findById(long id);

    List<Certificate> findAll();

    boolean update(Certificate certificate, long id) throws ParseException;

    boolean remove(long id);

    boolean addTagToCertificate(long certificateId, long tagId);

    boolean clearCertificateTags(long certificateId);
}
