package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateService {
    Certificate addCertificate(Certificate certificate);

    List<Certificate> findAllCertificates(Map<String, String> parameters);

    Certificate findCertificateById(long id);

    Certificate updateCertificate(Certificate certificate);

    boolean removeCertificateById(long id);

    Certificate patchCertificate(Certificate certificate);
}
