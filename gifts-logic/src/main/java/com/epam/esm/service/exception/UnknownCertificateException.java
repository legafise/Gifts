package com.epam.esm.service.exception;

public class UnknownCertificateException extends ServiceException {
    public UnknownCertificateException() {
    }

    public UnknownCertificateException(String message) {
        super(message);
    }

    public UnknownCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCertificateException(Throwable cause) {
        super(cause);
    }

    public UnknownCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
