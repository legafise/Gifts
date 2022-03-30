package com.epam.esm.dto;

import com.epam.esm.entity.Certificate;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.dto.EntityConstant.DATE_FORMAT_PATTERN;

public class OrderDto {
    private long id;
    private BigDecimal price;
    private LocalDateTime purchaseTime;
    private CertificateDto certificate;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public CertificateDto getCertificate() {
        return certificate;
    }

    public void setCertificate(CertificateDto certificate) {
        this.certificate = certificate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
