package com.epam.esm.service.exception;

public class MJCNotEnoughMoneyException extends MJCServiceException {
    public MJCNotEnoughMoneyException() {
    }

    public MJCNotEnoughMoneyException(String message) {
        super(message);
    }

    public MJCNotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MJCNotEnoughMoneyException(Throwable cause) {
        super(cause);
    }

    public MJCNotEnoughMoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
