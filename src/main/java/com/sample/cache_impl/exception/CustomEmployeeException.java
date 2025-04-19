package com.sample.cache_impl.exception;

import static com.sample.cache_impl.util.Constants.*;

public class CustomEmployeeException extends RuntimeException {

    private String errorMessage;
    private String errorCode;

    public static final CustomEmployeeException CUSTOM_INSERT_ERROR = new CustomEmployeeException(INSERT_ERROR, INSERT_ERROR_CODE);
    public static final CustomEmployeeException CUSTOM_UPDATE_ERROR = new CustomEmployeeException(UPDATE_ERROR, UPDATE_ERROR_CODE);
    public static final CustomEmployeeException CUSTOM_DELETE_ERROR = new CustomEmployeeException(DELETE_ERROR, DELETE_ERROR_CODE);
    public static final CustomEmployeeException CUSTOM_FETCH_ERROR = new CustomEmployeeException(FETCH_ERROR, FETCH_ERROR_CODE);
    public static final CustomEmployeeException CUSTOM_REDIS_ERROR = new CustomEmployeeException(REDIS_UNCLEAN_MESSAGE, FETCH_ERROR_CODE);

    public CustomEmployeeException(String errorMessage) {
        super(errorMessage);
    }

    public CustomEmployeeException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
