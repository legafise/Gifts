package com.epam.esm.service.exception;

public class MJCInvalidSortParameterException extends MJCServiceException {
    public MJCInvalidSortParameterException() {
        super();
    }

    public MJCInvalidSortParameterException(String message) {
        super(message);
    }

    public MJCInvalidSortParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCInvalidSortParameterException(Throwable cause) {
        super(cause);
    }

    protected MJCInvalidSortParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
