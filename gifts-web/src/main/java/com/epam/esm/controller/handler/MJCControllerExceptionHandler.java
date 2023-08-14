package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.MJCLocalizer;
import com.epam.esm.converter.MJCEntityConversionException;
import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.controller.handler.MJCEntityErrorCode.*;

@RestControllerAdvice
public class MJCControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final MJCLocalizer localizer;
    private static final String NUMBER_FORMAT_ERROR_MESSAGE = "invalid.number.value.was.entered";
    private static final String INVALID_CERTIFICATE_MESSAGE = "invalid.certificate";

    @Autowired
    public MJCControllerExceptionHandler(MJCLocalizer localizer) {
        this.localizer = localizer;
    }

    @ExceptionHandler(MJCTypedServiceException.class)
    public ResponseEntity<MJCErrorResponse> handleTypedServiceException(MJCTypedServiceException e) {
        if (e.getEntityClass().equals(Certificate.class) && e.getClass().equals(MJCInvalidEntityException.class)) {
            e = new MJCTypedServiceException(e.getEntityClass(),
                        String.format(localizer.toLocale(INVALID_CERTIFICATE_MESSAGE), e.getMessage()));
            }

        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                MJCEntityErrorCode.findErrorCodeByEntityClass(e.getEntityClass()).getErrorCode());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<MJCErrorResponse> handleNumberFormatException(NumberFormatException e) {
        return createErrorResponse(NUMBER_FORMAT_ERROR_MESSAGE, MJCExceptionErrorStatus.findHttpStatusByException(e),
                DEFAULT_ERROR_CODE.getErrorCode());
    }

    @ExceptionHandler(MJCNotEnoughMoneyException.class)
    public ResponseEntity<MJCErrorResponse> handleNotEnoughMoneyException(MJCNotEnoughMoneyException e) {
        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                USER_ERROR_CODE.getErrorCode());
    }

    @ExceptionHandler(MJCMissingPageNumberException.class)
    public ResponseEntity<MJCErrorResponse> handleMissingPageNumberException(MJCMissingPageNumberException e) {
        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                DEFAULT_ERROR_CODE.getErrorCode());
    }

    @ExceptionHandler(MJCInvalidSortParameterException.class)
    public ResponseEntity<MJCErrorResponse> handleInvalidSortParameterException(MJCInvalidSortParameterException e) {
        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                DEFAULT_ERROR_CODE.getErrorCode());
    }

    @ExceptionHandler(MJCInvalidPaginationDataException.class)
    public ResponseEntity<MJCErrorResponse> handleInvalidPaginationDataException(MJCInvalidPaginationDataException e) {
        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                DEFAULT_ERROR_CODE.getErrorCode());
    }

    @ExceptionHandler(MJCEntityConversionException.class)
    public ResponseEntity<MJCErrorResponse> handleMJCEntityConversionException(MJCEntityConversionException e) {
        return createErrorResponse(e.getMessage(), MJCExceptionErrorStatus.findHttpStatusByException(e),
                MJCEntityErrorCode.findErrorCodeByEntityClass(e.getEntityClass()).getErrorCode());
    }

    private ResponseEntity<MJCErrorResponse> createErrorResponse(String messageCode, HttpStatus status, String errorCode) {
        String errorMessage = localizer.toLocale(messageCode);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("errorMessage", errorMessage);
        responseBody.put("errorCode", status.value() + errorCode);

        MJCErrorResponse error = new MJCErrorResponse();
        error.setResponseBody(responseBody);

        return new ResponseEntity<>(error, status);
    }
}