package com.sample.cache_impl.exception;

public class CustomEmployeeException extends RuntimeException {

    private String errorMessage;
    private String errorCode;

    public CustomEmployeeException(String errorMessage) {
        super(errorMessage);
    }

    public CustomEmployeeException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
