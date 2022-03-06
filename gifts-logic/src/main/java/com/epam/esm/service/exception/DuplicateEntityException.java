package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class DuplicateEntityException extends EntityException {
    public DuplicateEntityException() {
        super();
    }

    public DuplicateEntityException(Class<? extends BaseEntity> entityClass, String message) {
        super(entityClass, message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

    protected DuplicateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
