package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.epam.esm.controller.handler.EntityErrorCodes.*;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Localizer localizer;
    private static final String NUMBER_FORMAT_ERROR_MESSAGE = "invalid.number.value.was.entered";

    @Autowired
    public ControllerExceptionHandler(Localizer localizer) {
        this.localizer = localizer;
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(EntityException e) {
        return EntityExceptionHandler.findExceptionHandler(e.getEntityClass(), e.getClass()).handle(e, localizer);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException() {
        return ErrorResponseCreator.createErrorResponse(NUMBER_FORMAT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughMoneyException(NotEnoughMoneyException e) {
        return ErrorResponseCreator.createErrorResponse(e.getMessage(), HttpStatus.PAYMENT_REQUIRED, USER_ERROR_CODE.getErrorCode(), localizer);
    }
}
