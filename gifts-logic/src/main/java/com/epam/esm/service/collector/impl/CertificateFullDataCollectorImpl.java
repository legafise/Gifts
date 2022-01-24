package com.epam.esm.service.collector.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.service.collector.CertificateFullDataCollector;
import org.springframework.stereotype.Component;

@Component
public class CertificateFullDataCollectorImpl implements CertificateFullDataCollector {
    @Override
    public Certificate collectFullCertificateData(Certificate certificateWithUpdate, Certificate actualCertificate) {
        addCertificateName(certificateWithUpdate, actualCertificate);
        addCertificateDescription(certificateWithUpdate, actualCertificate);
        addCertificateDuration(certificateWithUpdate, actualCertificate);
        addCertificatePrice(certificateWithUpdate, actualCertificate);
        addCertificateCreateDate(certificateWithUpdate, actualCertificate);
        addCertificateLastUpdateDate(certificateWithUpdate, actualCertificate);
        addCertificateTags(certificateWithUpdate, actualCertificate);

        return actualCertificate;
    }

    private void addCertificateName(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getName() != null) {
            actualCertificate.setName(certificateWithUpdate.getName());
        }
    }

    private void addCertificateDescription(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getDescription() != null) {
            actualCertificate.setDescription(certificateWithUpdate.getDescription());
        }
    }

    private void addCertificateDuration(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getDuration() > 0) {
            actualCertificate.setDuration(certificateWithUpdate.getDuration());
        }
    }

    private void addCertificatePrice(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getPrice() != null) {
            actualCertificate.setPrice(certificateWithUpdate.getPrice());
        }
    }

    private void addCertificateCreateDate(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getCreateDate() != null) {
            actualCertificate.setCreateDate(certificateWithUpdate.getCreateDate());
        }
    }

    private void addCertificateLastUpdateDate(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getLastUpdateDate() != null) {
            actualCertificate.setLastUpdateDate(certificateWithUpdate.getLastUpdateDate());
        }
    }

    private void addCertificateTags(Certificate certificateWithUpdate, Certificate actualCertificate) {
        if (certificateWithUpdate.getTags().size() > 0) {
            actualCertificate.setTags(certificateWithUpdate.getTags());
        }
    }
}
