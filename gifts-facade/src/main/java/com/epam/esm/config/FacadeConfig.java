package com.epam.esm.config;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.converter.impl.DefaultMJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.populator.MJCCertificatePopulatorImpl;
import com.epam.esm.populator.MJCOrderPopulator;
import com.epam.esm.populator.MJCTagPopulator;
import com.epam.esm.populator.MJCUserPopulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class FacadeConfig {
    @Bean
    public MJCConverter<Certificate, CertificateDto> mjcCertificateConverter(MJCCertificatePopulatorImpl certificatePopulator) {
        DefaultMJCConverter<Certificate, CertificateDto> converter = new DefaultMJCConverter<>(CertificateDto.class);
        converter.setPopulators(Collections.singletonList(certificatePopulator));
        return converter;
    }

    @Bean
    public MJCConverter<Tag, TagDto> mjcTagConverter(MJCTagPopulator tagPopulator) {
        DefaultMJCConverter<Tag, TagDto> converter = new DefaultMJCConverter<>(TagDto.class);
        converter.setPopulators(Collections.singletonList(tagPopulator));
        return converter;
    }

    @Bean
    public MJCConverter<Order, OrderDto> mjcOrderConverter(MJCOrderPopulator orderPopulator) {
        DefaultMJCConverter<Order, OrderDto> converter = new DefaultMJCConverter<>(OrderDto.class);
        converter.setPopulators(Collections.singletonList(orderPopulator));
        return converter;
    }

    @Bean
    public MJCConverter<User, UserDto> mjcUserConverter(MJCUserPopulator userPopulator) {
        DefaultMJCConverter<User, UserDto> converter = new DefaultMJCConverter<>(UserDto.class);
        converter.setPopulators(Collections.singletonList(userPopulator));
        return converter;
    }
}
