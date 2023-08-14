package com.epam.esm.service.collector;

import com.epam.esm.entity.Certificate;

public interface MJCCertificateFullDataCollector {
    Certificate collectFullCertificateData(Certificate certificateWithUpdate, Certificate actualCertificate);
}
