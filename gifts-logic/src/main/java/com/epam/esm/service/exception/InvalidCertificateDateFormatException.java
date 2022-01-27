package com.epam.esm.service.exception;

public class InvalidCertificateDateFormatException extends ServiceException {
    public InvalidCertificateDateFormatException() {
    }

    public InvalidCertificateDateFormatException(String message) {
        super(message);
    }

    public InvalidCertificateDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCertificateDateFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidCertificateDateFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
