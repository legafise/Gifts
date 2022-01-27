package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.service.exception.*;
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
    private static final String NUMBER_FORMAT_ERROR_MESSAGE = "invalid.number.value.was.entered";
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
    public ResponseEntity<ErrorResponse> handleInvalidTagException(InvalidTagException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, TAG_ERROR_CODE);
    }

    @ExceptionHandler(DuplicateTagException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTagException(DuplicateTagException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT, TAG_ERROR_CODE);
    }

    @ExceptionHandler(DuplicateCertificateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCertificateException(DuplicateCertificateException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.CONFLICT, CERTIFICATE_ERROR_CODE);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException() {
        return getErrorResponse(NUMBER_FORMAT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, "");
    }

    @ExceptionHandler(InvalidCertificateDateFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateFormatException(InvalidCertificateDateFormatException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, CERTIFICATE_ERROR_CODE);
    }

    @ExceptionHandler(UnknownCertificateException.class)
    public ResponseEntity<ErrorResponse> handleUnknownCertificateException(UnknownCertificateException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, CERTIFICATE_ERROR_CODE);
    }

    @ExceptionHandler(UnknownTagException.class)
    public ResponseEntity<ErrorResponse> handleUUnknownTagException(UnknownTagException e) {
        return getErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, TAG_ERROR_CODE);
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
