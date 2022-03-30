package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.facade.MJCCertificateFacade;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MJCCertificateFacadeImpl implements MJCCertificateFacade {
    @Autowired
    @Qualifier("mjcCertificateConverter")
    private MJCConverter<Certificate, CertificateDto> certificateConverter;
    @Autowired
    private CertificateService certificateService;

    public void setCertificateService(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    public void setCertificateConverter(MJCConverter<Certificate, CertificateDto> certificateConverter) {
        this.certificateConverter = certificateConverter;
    }

    @Override
    public CertificateDto findCertificateById(long id) {
        return certificateConverter.convert(certificateService.findCertificateById(id));
    }

    @Override
    public CertificateDto addCertificate(Certificate certificate) {
        return certificateConverter.convert(certificateService.addCertificate(certificate));
    }

    @Override
    public List<CertificateDto> findAllCertificates(Map<String, String> parameters, List<String> tagNames) {
        return certificateConverter.convertAll(certificateService.findAllCertificates(parameters, tagNames));
    }

    @Override
    public CertificateDto updateCertificate(Certificate certificate) {
        return certificateConverter.convert(certificateService.updateCertificate(certificate));
    }

    @Override
    public void removeCertificateById(long id) {
        certificateService.removeCertificateById(id);
    }

    @Override
    public CertificateDto patchCertificate(Certificate certificate) {
        return certificateConverter.convert(certificateService.patchCertificate(certificate));
    }
}
