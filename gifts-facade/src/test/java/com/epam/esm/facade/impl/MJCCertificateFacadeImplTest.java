package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.MJCCertificateService;
import com.epam.esm.service.constant.MJCPaginationConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

class MJCCertificateFacadeImplTest {
    private MJCCertificateFacadeImpl certificateFacade;
    private MJCConverter<Certificate, CertificateDto> certificateConverter;
    private MJCCertificateService certificateService;
    private Certificate testCertificate;
    private CertificateDto testCertificateDto;
    private List<CertificateDto> certificateDtoList;
    private List<Certificate> certificateList;

    @BeforeEach
     void setUp() {
        certificateConverter = (MJCConverter<Certificate, CertificateDto>) mock(MJCConverter.class);
        certificateService = mock(MJCCertificateService.class);
        certificateFacade = new MJCCertificateFacadeImpl();
        certificateFacade.setCertificateConverter(certificateConverter);
        certificateFacade.setCertificateService(certificateService);

        Tag firstTestTag = new Tag(102, "Jumps");
        Tag secondTestTag = new Tag(103, "Entertainment");
        Tag thirdTestTag = new Tag(101, "Tattoo");
        TagDto firstTestTagDto = new TagDto(102, "Jumps");
        TagDto secondTestTagDto = new TagDto(103, "Entertainment");
        TagDto thirdTestTagDto = new TagDto(101, "Tattoo");

        testCertificate = new Certificate(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), new HashSet<>(Collections.singletonList(thirdTestTag)));
        testCertificateDto = new CertificateDto(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(thirdTestTagDto));
        Certificate secondTestCertificate = new Certificate(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), new HashSet<>(Arrays.asList(firstTestTag, secondTestTag)));
        CertificateDto secondTestCertificateDto = new CertificateDto(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Arrays.asList(firstTestTagDto, secondTestTagDto));

        certificateList = Arrays.asList(testCertificate, secondTestCertificate);
        certificateDtoList = Arrays.asList(testCertificateDto, secondTestCertificateDto);
    }

    @Test
    void findCertificateByIdTest() {
        when(certificateService.findCertificateById(1)).thenReturn(testCertificate);
        when(certificateConverter.convert(testCertificate)).thenReturn(testCertificateDto);
        Assertions.assertEquals(testCertificateDto, certificateFacade.findCertificateById(1));
    }

    @Test
    void addCertificateTest() {
        when(certificateService.addCertificate(testCertificate)).thenReturn(testCertificate);
        when(certificateConverter.convert(testCertificate)).thenReturn(testCertificateDto);
        Assertions.assertEquals(testCertificateDto, certificateFacade.addCertificate(testCertificate));
    }

    @Test
    void findAllCertificatesTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");
        when(certificateService.findAllCertificates(pageParameters, Collections.emptyList())).thenReturn(certificateList);
        when(certificateConverter.convertAll(certificateList)).thenReturn(certificateDtoList);
        Assertions.assertEquals(certificateDtoList, certificateFacade.findAllCertificates(pageParameters, Collections.emptyList()));
    }

    @Test
    void updateCertificate() {
        testCertificate.setName("UpdatedCertificate");
        testCertificateDto.setName("UpdatedCertificate");
        when(certificateService.updateCertificate(testCertificate)).thenReturn(testCertificate);
        when(certificateConverter.convert(testCertificate)).thenReturn(testCertificateDto);
        Assertions.assertEquals(testCertificateDto, certificateFacade.updateCertificate(testCertificate));
    }

    @Test
    void removeCertificateByIdTest() {
        doNothing().when(certificateService).removeCertificateById(1);
        certificateFacade.removeCertificateById(1);
    }

    @Test
    void patchCertificate() {
        Certificate certificateForPatch = new Certificate();
        certificateForPatch.setId(1);
        certificateForPatch.setName("UpdatedCertificate");
        testCertificate.setName("UpdatedCertificate");
        testCertificateDto.setName("UpdatedCertificate");
        when(certificateService.patchCertificate(certificateForPatch)).thenReturn(testCertificate);
        when(certificateConverter.convert(testCertificate)).thenReturn(testCertificateDto);
        Assertions.assertEquals(testCertificateDto, certificateFacade.patchCertificate(certificateForPatch));
    }
}