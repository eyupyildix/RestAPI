package com.eyupyildix.endpoint;

public class EndpointException extends RuntimeException {

    private int statusCode = 500;

    public EndpointException() {
    }

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
