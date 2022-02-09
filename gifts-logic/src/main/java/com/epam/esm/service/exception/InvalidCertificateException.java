package com.epam.esm.service.exception;

public class InvalidCertificateException extends ServiceException {
    public InvalidCertificateException() {
    }

    public InvalidCertificateException(String message) {
        super(message);
    }

    public InvalidCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCertificateException(Throwable cause) {
        super(cause);
    }

    public InvalidCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
