package com.epam.esm.service.exception;

public class DuplicateCertificateException extends ServiceException {
    public DuplicateCertificateException() {
    }

    public DuplicateCertificateException(String message) {
        super(message);
    }

    public DuplicateCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCertificateException(Throwable cause) {
        super(cause);
    }

    public DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
