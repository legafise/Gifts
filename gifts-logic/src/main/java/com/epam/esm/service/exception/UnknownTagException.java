package com.epam.esm.service.exception;

public class UnknownTagException extends ServiceException {
    public UnknownTagException() {
    }

    public UnknownTagException(String message) {
        super(message);
    }

    public UnknownTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTagException(Throwable cause) {
        super(cause);
    }

    public UnknownTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
