package com.epam.esm.controller.handler;

import com.epam.esm.controller.handler.ErrorResponse;
import com.epam.esm.controller.localizer.Localizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class ErrorResponseCreator {
    private ErrorResponseCreator() {
    }

    public static ResponseEntity<ErrorResponse> createErrorResponse(String messageCode, HttpStatus status, String errorCode, Localizer localizer) {
        String errorMessage = localizer.toLocale(messageCode);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("errorMessage", errorMessage);
        responseBody.put("errorCode", status.value() + errorCode);

        ErrorResponse error = new ErrorResponse();
        error.setResponseBody(responseBody);

        return new ResponseEntity<>(error, status);
    }
}
