package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.service.exception.DuplicateTagException;
import com.epam.esm.service.exception.InvalidCertificateException;
import com.epam.esm.service.exception.InvalidTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Localizer localizer;
    private static final String CERTIFICATE_ERROR_CODE = "01";
    private static final String TAG_ERROR_CODE = "02";

    @Autowired
    public ControllerExceptionHandler(Localizer localizer) {
        this.localizer = localizer;
    }

    @ExceptionHandler(InvalidCertificateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCertificateException(InvalidCertificateException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, CERTIFICATE_ERROR_CODE);
    }

    @ExceptionHandler(InvalidTagException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCertificateException(InvalidTagException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, TAG_ERROR_CODE);
    }

    @ExceptionHandler(DuplicateTagException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTagException(DuplicateTagException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT, TAG_ERROR_CODE);
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(String messageCode, HttpStatus status, String errorCode) {
        String errorMessage = localizer.toLocale(messageCode);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("errorMessage", errorMessage);
        responseBody.put("errorCode", status.value() + errorCode);

        ErrorResponse error = new ErrorResponse();
        error.setResponseBody(responseBody);

        return new ResponseEntity<>(error, status);
    }
}
