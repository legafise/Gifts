package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService {
    Certificate addCertificate(Certificate certificate);
    List<Certificate> findAllCertificates();
    Certificate findCertificateById(String id);
    Certificate updateCertificate(Certificate certificate, String id);
    boolean removeCertificateById(String id);
    Certificate patchCertificate(Certificate certificate, String id);
}
