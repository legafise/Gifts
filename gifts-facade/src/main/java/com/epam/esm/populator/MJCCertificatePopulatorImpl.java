package com.epam.esm.populator;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MJCCertificatePopulatorImpl implements MJCPopulator<Certificate, CertificateDto> {
    @Autowired
    @Qualifier("mjcTagConverter")
    private MJCConverter<Tag, TagDto> tagConverter;

    public void setTagConverter(MJCConverter<Tag, TagDto> tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Override
    public void populate(Certificate certificate, CertificateDto certificateDto) {
        certificateDto.setId(certificate.getId());
        certificateDto.setName(certificate.getName());
        certificateDto.setDescription(certificate.getDescription());
        certificateDto.setPrice(certificate.getPrice());
        certificateDto.setDuration(certificate.getDuration());
        certificateDto.setCreateDate(certificate.getCreateDate());
        certificateDto.setLastUpdateDate(certificate.getLastUpdateDate());
        certificateDto.setTags(tagConverter.convertAll(certificate.getTags()));
    }
}
