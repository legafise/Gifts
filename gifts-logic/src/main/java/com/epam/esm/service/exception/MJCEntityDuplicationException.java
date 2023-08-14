package com.epam.esm.service.exception;

import com.epam.esm.entity.BaseEntity;

public class MJCEntityDuplicationException extends MJCTypedServiceException {
    public MJCEntityDuplicationException() {
        super();
    }

    public MJCEntityDuplicationException(Class<? extends BaseEntity> entityClass, String message) {
        super(entityClass, message);
    }

    public MJCEntityDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCEntityDuplicationException(Throwable cause) {
        super(cause);
    }

    protected MJCEntityDuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
