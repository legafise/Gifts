package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.facade.MJCCertificateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private static final String TAG_INFO_MESSAGE = "Tag(%s) information";
    private final MJCCertificateFacade certificateFacade;

    @Autowired
    public CertificateController(MJCCertificateFacade certificateFacade) {
        this.certificateFacade = certificateFacade;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<EntityModel<CertificateDto>> readAllCertificates(@RequestParam Map<String, String> parameters,
                                                                 @RequestParam(required = false) List<String> tagNames) {
        parameters.remove("tagNames");
        List<CertificateDto> readCertificates = certificateFacade.findAllCertificates(parameters, tagNames);

        return readCertificates.stream()
                .map(this::createHateoasCertificateModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<CertificateDto> readCertificateById(@PathVariable long id) {
        CertificateDto readCertificate = certificateFacade.findCertificateById(id);

        return createHateoasCertificateModel(readCertificate);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EntityModel<CertificateDto> createCertificate(@RequestBody Certificate certificate) {
        CertificateDto createdCertificate = certificateFacade.addCertificate(certificate);
        return createHateoasCertificateModel(createdCertificate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<CertificateDto> updateCertificate(@RequestBody Certificate certificate, @PathVariable long id) {
        certificate.setId(id);
        CertificateDto updatedCertificate = certificateFacade.updateCertificate(certificate);
        return createHateoasCertificateModel(updatedCertificate);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<CertificateDto> patchCertificate(@RequestBody Certificate certificate, @PathVariable long id) {
        certificate.setId(id);
        CertificateDto patchedCertificate = certificateFacade.patchCertificate(certificate);
        return createHateoasCertificateModel(patchedCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateFacade.removeCertificateById(id);
    }

    private EntityModel<CertificateDto> createHateoasCertificateModel(CertificateDto certificate) {
        EntityModel<CertificateDto> certificateModel = EntityModel.of(certificate);
        certificate.getTags().forEach(tag -> {
            WebMvcLinkBuilder linkToTag = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                    .readTag(tag.getId()));
            certificateModel.add(linkToTag.withRel(String.format(TAG_INFO_MESSAGE, tag.getName())));
        });

        return certificateModel;
    }
}