package com.epam.esm.controller.handler;

import java.util.Map;
import java.util.Objects;

public class ErrorResponse {
    private Map<String, String> responseBody;

    public ErrorResponse(Map<String, String> responseBody) {
        this.responseBody = responseBody;
    }

    public ErrorResponse() {
    }

    public Map<String, String> getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Map<String, String> responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseBody);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "responseBody=" + responseBody +
                '}';
    }
}
