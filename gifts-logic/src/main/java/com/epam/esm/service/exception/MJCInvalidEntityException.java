package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class MJCInvalidEntityException extends MJCTypedServiceException {
    public MJCInvalidEntityException() {
        super();
    }

    public MJCInvalidEntityException(Class<? extends BaseEntity> entityClass, String message) {
        super(entityClass, message);
    }

    public MJCInvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCInvalidEntityException(Throwable cause) {
        super(cause);
    }

    protected MJCInvalidEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
