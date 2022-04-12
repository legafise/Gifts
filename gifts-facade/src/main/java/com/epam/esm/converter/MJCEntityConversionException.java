package com.epam.esm.converter;

import com.epam.esm.entity.BaseEntity;

public class MJCEntityConversionException extends RuntimeException {
    private Class<? extends BaseEntity> entityClass;

    public MJCEntityConversionException() {
        super();
    }

    public MJCEntityConversionException(Class<? extends BaseEntity> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public MJCEntityConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCEntityConversionException(Throwable cause) {
        super(cause);
    }

    protected MJCEntityConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }
}
