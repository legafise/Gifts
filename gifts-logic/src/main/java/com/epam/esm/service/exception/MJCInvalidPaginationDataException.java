package com.epam.esm.service.exception;

public class MJCInvalidPaginationDataException extends MJCServiceException {
    public MJCInvalidPaginationDataException() {
        super();
    }

    public MJCInvalidPaginationDataException(String message) {
        super(message);
    }

    public MJCInvalidPaginationDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCInvalidPaginationDataException(Throwable cause) {
        super(cause);
    }

    protected MJCInvalidPaginationDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
