package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class MJCUnknownEntityException extends MJCTypedServiceException {
    public MJCUnknownEntityException() {
        super();
    }

    public MJCUnknownEntityException(Class<? extends BaseEntity> entityClass, String message) {
        super(entityClass, message);
    }

    public MJCUnknownEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCUnknownEntityException(Throwable cause) {
        super(cause);
    }

    protected MJCUnknownEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
