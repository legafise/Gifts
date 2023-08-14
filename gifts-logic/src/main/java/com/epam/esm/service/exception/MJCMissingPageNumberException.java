package com.epam.esm.service.exception;

public class MJCMissingPageNumberException extends MJCServiceException {
    public MJCMissingPageNumberException() {
        super();
    }

    public MJCMissingPageNumberException(String message) {
        super(message);
    }

    public MJCMissingPageNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCMissingPageNumberException(Throwable cause) {
        super(cause);
    }

    protected MJCMissingPageNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
