package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateDao {
    boolean add(Certificate certificate);

    Optional<Certificate> findById(long id);

    List<Certificate> findAll();

    boolean update(Certificate certificate);

    boolean remove(long id);
}
