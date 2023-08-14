package com.epam.esm.controller.handler;

import com.epam.esm.service.exception.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public enum MJCExceptionErrorStatus {
    NOT_FOUNT_EXCEPTION(Collections.singletonList(MJCUnknownEntityException.class), HttpStatus.NOT_FOUND),
    CONFLICT_EXCEPTION(Collections.singletonList(MJCEntityDuplicationException.class), HttpStatus.CONFLICT),
    BAD_REQUEST_EXCEPTION(Arrays.asList(MJCInvalidEntityException.class, NumberFormatException.class,
            MJCMissingPageNumberException.class, MJCInvalidSortParameterException.class,
            MJCInvalidPaginationDataException.class), HttpStatus.BAD_REQUEST),
    PAYMENT_REQUIRED_EXCEPTION(Collections.singletonList(MJCNotEnoughMoneyException.class), HttpStatus.PAYMENT_REQUIRED),
    UNKNOWN_EXCEPTION(Collections.singletonList(RuntimeException.class), HttpStatus.BAD_REQUEST);

    private final List<Class<? extends RuntimeException>> exceptionClasses;
    private final HttpStatus httpStatus;

    MJCExceptionErrorStatus(List<Class<? extends RuntimeException>> exceptionClasses, HttpStatus httpStatus) {
        this.exceptionClasses = exceptionClasses;
        this.httpStatus = httpStatus;
    }

    public List<Class<? extends RuntimeException>> getExceptionClasses() {
        return exceptionClasses;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static HttpStatus findHttpStatusByException(RuntimeException exception) {
        return Arrays.stream(MJCExceptionErrorStatus.values())
                .filter(errorStatus -> errorStatus.getExceptionClasses().contains(exception.getClass()))
                .findFirst()
                .map(MJCExceptionErrorStatus::getHttpStatus)
                .orElse(UNKNOWN_EXCEPTION.getHttpStatus());
    }
}
