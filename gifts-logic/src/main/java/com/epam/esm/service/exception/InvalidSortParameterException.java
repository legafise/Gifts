package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class InvalidSortParameterException extends EntityException {
    public InvalidSortParameterException() {
        super();
    }

    public InvalidSortParameterException(Class<? extends BaseEntity> entityClass, String message) {
        super(entityClass, message);
    }

    public InvalidSortParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSortParameterException(Throwable cause) {
        super(cause);
    }

    protected InvalidSortParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
