package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Certificate> readAllCertificates(@RequestParam Map<String, String> parameters,
                                                 @RequestParam(required = false) List<String> tagNames) {
        parameters.remove("tagNames");
        return certificateService.findAllCertificates(parameters, tagNames);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Certificate readCertificateById(@PathVariable long id) {
        return certificateService.findCertificateById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.addCertificate(certificate);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public Certificate updateCertificate(@RequestBody Certificate certificate, @PathVariable long id) {
        certificate.setId(id);
        return certificateService.updateCertificate(certificate);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(OK)
    public Certificate patchCertificate(@RequestBody Certificate certificate, @PathVariable long id) {
        certificate.setId(id);
        return certificateService.patchCertificate(certificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        certificateService.removeCertificateById(id);
    }
}
