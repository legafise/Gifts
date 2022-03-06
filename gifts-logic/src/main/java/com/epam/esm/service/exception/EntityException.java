package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class EntityException extends RuntimeException {
    private Class<? extends BaseEntity> entityClass;

    public EntityException() {
        super();
    }

    public EntityException(Class<? extends BaseEntity> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityException(Throwable cause) {
        super(cause);
    }

    protected EntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }
}
