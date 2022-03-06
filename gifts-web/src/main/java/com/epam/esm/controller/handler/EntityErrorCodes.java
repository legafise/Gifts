package com.epam.esm.controller.handler;

public enum EntityErrorCodes {
    DEFAULT_ERROR_CODE(""),
    CERTIFICATE_ERROR_CODE("01"),
    TAG_ERROR_CODE("02"),
    USER_ERROR_CODE("03"),
    ORDER_ERROR_CODE("04");

    private final String errorCode;

    EntityErrorCodes(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
