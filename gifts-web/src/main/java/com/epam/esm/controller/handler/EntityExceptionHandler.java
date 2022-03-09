package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.exception.EntityDuplicationException;
import com.epam.esm.service.exception.TypedServiceException;
import com.epam.esm.service.exception.InvalidEntityException;
import com.epam.esm.service.exception.UnknownEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static com.epam.esm.controller.handler.EntityErrorCode.DEFAULT_ERROR_CODE;


public enum EntityExceptionHandler {
    UNKNOWN_ENTITY_HANDLER(UnknownEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(TypedServiceException typedServiceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(typedServiceException.getMessage(), HttpStatus.NOT_FOUND,
                    EntityErrorCode.findErrorCodeByEntityClass(typedServiceException.getEntityClass()).getErrorCode(), localizer);
        }
    },
    DUPLICATE_ENTITY_HANDLER(EntityDuplicationException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(TypedServiceException typedServiceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(typedServiceException.getMessage(), HttpStatus.CONFLICT,
                    EntityErrorCode.findErrorCodeByEntityClass(typedServiceException.getEntityClass()).getErrorCode(), localizer);
        }
    },
    INVALID_ENTITY_HANDLER(InvalidEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(TypedServiceException typedServiceException, Localizer localizer) {
            if (typedServiceException.getEntityClass().equals(Certificate.class)) {
                typedServiceException = new TypedServiceException(typedServiceException.getEntityClass(),
                        String.format(localizer.toLocale(INVALID_CERTIFICATE_MESSAGE), typedServiceException.getMessage()));
            }

            return ErrorResponseCreator.createErrorResponse(typedServiceException.getMessage(), HttpStatus.BAD_REQUEST,
                    EntityErrorCode.findErrorCodeByEntityClass(typedServiceException.getEntityClass()).getErrorCode(), localizer);
        }
    },
    STANDARD_HANDLER(TypedServiceException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(TypedServiceException typedServiceExceptionClass, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(UNKNOWN_EXCEPTION_MESSAGE,
                    HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
        }
    };

    private static final String UNKNOWN_EXCEPTION_MESSAGE = "unknown.exception";
    private static final String INVALID_CERTIFICATE_MESSAGE = "invalid.certificate";
    private final Class<? extends TypedServiceException> entityExceptionClass;

    EntityExceptionHandler(Class<? extends TypedServiceException> entityExceptionClass) {
        this.entityExceptionClass = entityExceptionClass;
    }

    public Class<? extends TypedServiceException> getEntityExceptionClass() {
        return entityExceptionClass;
    }

    abstract ResponseEntity<ErrorResponse> handle(TypedServiceException typedServiceException, Localizer localizer);

    public static EntityExceptionHandler findExceptionHandler(TypedServiceException typedServiceException) {
        return Arrays.stream(EntityExceptionHandler.values())
                .filter(handler -> handler.entityExceptionClass.equals(typedServiceException.getClass()))
                .findFirst()
                .orElse(STANDARD_HANDLER);
    }
}
