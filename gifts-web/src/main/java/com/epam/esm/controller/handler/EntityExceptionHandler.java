package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.entity.*;
import com.epam.esm.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static com.epam.esm.controller.handler.EntityErrorCodes.*;

public enum EntityExceptionHandler {
    UNKNOWN_TAG_HANDLER(Tag.class, UnknownEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.NOT_FOUND,
                    TAG_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    UNKNOWN_CERTIFICATE_HANDLER(Certificate.class, UnknownEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.NOT_FOUND,
                    TAG_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    UNKNOWN_USER_HANDLER(User.class, UnknownEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.NOT_FOUND,
                    USER_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    UNKNOWN_ORDER_HANDLER(Order.class, UnknownEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.NOT_FOUND,
                    ORDER_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    DUPLICATE_TAG_HANDLER(Tag.class, DuplicateEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.CONFLICT,
                    TAG_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    DUPLICATE_CERTIFICATE_HANDLER(Certificate.class, DuplicateEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.CONFLICT,
                    CERTIFICATE_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    INVALID_TAG_HANDLER(Tag.class, InvalidEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(), HttpStatus.BAD_REQUEST,
                    TAG_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    INVALID_CERTIFICATE_HANDLER(Certificate.class, InvalidEntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            String errorMessage = String.format(localizer.toLocale(INVALID_CERTIFICATE_MESSAGE),
                    serviceException.getMessage());
            return ErrorResponseCreator.createErrorResponse(errorMessage, HttpStatus.BAD_REQUEST,
                    CERTIFICATE_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    INVALID_CERTIFICATES_SORT_PARAMETER_HANDLER(Certificate.class, InvalidSortParameterException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(serviceException.getMessage(),
                    HttpStatus.BAD_REQUEST, CERTIFICATE_ERROR_CODE.getErrorCode(), localizer);
        }
    },
    STANDARD_HANDLER(BaseEntity.class, EntityException.class) {
        @Override
        ResponseEntity<ErrorResponse> handle(EntityException serviceException, Localizer localizer) {
            return ErrorResponseCreator.createErrorResponse(UNKNOWN_EXCEPTION_MESSAGE,
                    HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
        }
    };

    private static final String UNKNOWN_EXCEPTION_MESSAGE = "unknown.exception";
    private static final String INVALID_CERTIFICATE_MESSAGE = "invalid.certificate";
    private final Class<? extends BaseEntity> entityClass;
    private final Class<? extends EntityException> serviceExceptionClass;

    EntityExceptionHandler(Class<? extends BaseEntity> entityClass, Class<? extends EntityException> serviceExceptionClass) {
        this.entityClass = entityClass;
        this.serviceExceptionClass = serviceExceptionClass;
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }

    public Class<? extends EntityException> getServiceExceptionClass() {
        return serviceExceptionClass;
    }

    abstract ResponseEntity<ErrorResponse> handle(EntityException entityException, Localizer localizer);

    public static EntityExceptionHandler findExceptionHandler(Class<? extends BaseEntity> entityClass, Class<? extends EntityException> serviceExceptionClass) {
        return Arrays.stream(EntityExceptionHandler.values())
                .filter(handler -> handler.getEntityClass().equals(entityClass)
                        && handler.serviceExceptionClass.equals(serviceExceptionClass))
                .findFirst()
                .orElse(STANDARD_HANDLER);
    }
}
