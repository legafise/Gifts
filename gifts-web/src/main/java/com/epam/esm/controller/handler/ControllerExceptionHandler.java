package com.epam.esm.controller.handler;

import com.epam.esm.controller.localizer.Localizer;
import com.epam.esm.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.epam.esm.controller.handler.EntityErrorCode.*;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Localizer localizer;
    private static final String NUMBER_FORMAT_ERROR_MESSAGE = "invalid.number.value.was.entered";

    @Autowired
    public ControllerExceptionHandler(Localizer localizer) {
        this.localizer = localizer;
    }

    @ExceptionHandler(TypedServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(TypedServiceException e) {
        return EntityExceptionHandler.findExceptionHandler(e).handle(e, localizer);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException() {
        return ErrorResponseCreator.createErrorResponse(NUMBER_FORMAT_ERROR_MESSAGE, HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughMoneyException(NotEnoughMoneyException e) {
        return ErrorResponseCreator.createErrorResponse(e.getMessage(), HttpStatus.PAYMENT_REQUIRED, USER_ERROR_CODE.getErrorCode(), localizer);
    }

    @ExceptionHandler(MissingPageNumberException.class)
    public ResponseEntity<ErrorResponse> handleMissingPageNumberException(MissingPageNumberException e) {
        return ErrorResponseCreator.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
    }

    @ExceptionHandler(InvalidSortParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSortParameterException(InvalidSortParameterException e) {
        return ErrorResponseCreator.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
    }

    @ExceptionHandler(InvalidPaginationDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPaginationDataException(InvalidPaginationDataException e) {
        return ErrorResponseCreator.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, DEFAULT_ERROR_CODE.getErrorCode(), localizer);
    }
}
