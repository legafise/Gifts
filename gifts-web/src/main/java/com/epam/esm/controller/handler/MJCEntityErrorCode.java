package com.epam.esm.controller.handler;

import com.epam.esm.entity.*;

import java.util.Arrays;

public enum MJCEntityErrorCode {
    DEFAULT_ERROR_CODE(BaseEntity.class, ""),
    CERTIFICATE_ERROR_CODE(Certificate.class,"01"),
    TAG_ERROR_CODE(Tag.class, "02"),
    USER_ERROR_CODE(User.class, "03"),
    ORDER_ERROR_CODE(Order.class, "04");

    private final Class<? extends BaseEntity> baseEntityClass;
    private final String errorCode;

    MJCEntityErrorCode(Class<? extends BaseEntity> baseEntityClass, String errorCode) {
        this.baseEntityClass = baseEntityClass;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Class<? extends BaseEntity> getBaseEntityClass() {
        return baseEntityClass;
    }

    public static MJCEntityErrorCode findErrorCodeByEntityClass(Class<? extends BaseEntity> baseEntityClass) {
        return Arrays.stream(MJCEntityErrorCode.values())
                .filter(entityErrorCode -> entityErrorCode.getBaseEntityClass().equals(baseEntityClass))
                .findFirst()
                .orElse(DEFAULT_ERROR_CODE);
    }
}
