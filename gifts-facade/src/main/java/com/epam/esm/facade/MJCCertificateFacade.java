package com.epam.esm.facade;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface MJCCertificateFacade {
    CertificateDto findCertificateById(long id);

    CertificateDto addCertificate(Certificate certificate);

    List<CertificateDto> findAllCertificates(Map<String, String> parameters, List<String> tagNames);

    CertificateDto updateCertificate(Certificate certificate);

    void removeCertificateById(long id);

    CertificateDto patchCertificate(Certificate certificate);
}
