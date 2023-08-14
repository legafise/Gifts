package com.epam.esm.service.exception;

public class MJCServiceException extends RuntimeException {
    public MJCServiceException() {
        super();
    }

    public MJCServiceException(String message) {
        super(message);
    }

    public MJCServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCServiceException(Throwable cause) {
        super(cause);
    }

    protected MJCServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
