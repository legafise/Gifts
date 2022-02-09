package com.epam.esm.service.exception;

public class DuplicateTagException extends ServiceException {
    public DuplicateTagException() {
    }

    public DuplicateTagException(String message) {
        super(message);
    }

    public DuplicateTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTagException(Throwable cause) {
        super(cause);
    }

    public DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
