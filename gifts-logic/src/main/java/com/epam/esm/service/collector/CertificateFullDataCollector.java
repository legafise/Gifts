package com.epam.esm.service.collector;

import com.epam.esm.entity.Certificate;

public interface CertificateFullDataCollector {
    Certificate collectFullCertificateData(Certificate certificateWithUpdate, Certificate actualCertificate);
}
