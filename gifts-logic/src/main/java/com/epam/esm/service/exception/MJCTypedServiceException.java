package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class MJCTypedServiceException extends MJCServiceException {
    private Class<? extends BaseEntity> entityClass;

    public MJCTypedServiceException() {
        super();
    }

    public MJCTypedServiceException(Class<? extends BaseEntity> entityClass, String message) {
        super(message);
        this.entityClass = entityClass;
    }

    public MJCTypedServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCTypedServiceException(Throwable cause) {
        super(cause);
    }

    protected MJCTypedServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }
}
