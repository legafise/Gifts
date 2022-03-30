package com.epam.esm.populator;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MJCOrderPopulator implements MJCPopulator<Order, OrderDto> {
    @Autowired
    @Qualifier("mjcCertificateConverter")
    private MJCConverter<Certificate, CertificateDto> certificateConverter;

    public void setCertificateConverter(MJCConverter<Certificate, CertificateDto> certificateConverter) {
        this.certificateConverter = certificateConverter;
    }

    @Override
    public void populate(Order order, OrderDto orderDto) {
        orderDto.setId(order.getId());
        orderDto.setPurchaseTime(order.getPurchaseTime());
        orderDto.setPrice(order.getPrice());
        orderDto.setCertificate(certificateConverter.convert(order.getCertificate()));
    }
}
