package com.epam.esm.dto;

import com.epam.esm.entity.Certificate;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.epam.esm.dto.EntityConstant.DATE_FORMAT_PATTERN;

public class OrderDto {
    private long id;
    private BigDecimal price;
    private LocalDateTime purchaseTime;
    private CertificateDto certificate;

    public OrderDto(long id, CertificateDto certificate, BigDecimal price, LocalDateTime purchaseTime) {
        this.id = id;
        this.certificate = certificate;
        this.price = price;
        this.purchaseTime = purchaseTime;
    }

    public OrderDto() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && Objects.equals(price, orderDto.price) && Objects.equals(purchaseTime, orderDto.purchaseTime) && Objects.equals(certificate, orderDto.certificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, purchaseTime, certificate);
    }
}
