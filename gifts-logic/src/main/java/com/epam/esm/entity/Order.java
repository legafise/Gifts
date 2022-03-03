package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.epam.esm.entity.EntityConstant.DATE_FORMAT_PATTERN;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private long id;

    @ManyToOne()
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
    private BigDecimal price;
    private LocalDateTime purchaseTime;

    public Order() {
    }

    public Order(long id, Certificate certificate, BigDecimal price, LocalDateTime purchaseTime) {
        this.id = id;
        this.certificate = certificate;
        this.price = price;
        this.purchaseTime = purchaseTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Certificate getCertificates() {
        return certificate;
    }

    public void setCertificates(Certificate certificate) {
        this.certificate = certificate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(certificate, order.certificate) && Objects.equals(price, order.price) && Objects.equals(purchaseTime, order.purchaseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificate, price, purchaseTime);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", certificates=" + certificate +
                ", price=" + price +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
