package com.sample.cache_impl.exception;

import com.sample.cache_impl.model.EmployeeValidatorErrorResponse;
import com.sample.cache_impl.model.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CustomEmployeeExceptionHandler {


    private static final String GENERIC_ERROR_CODE = "GEN-001";
    private static final String VALIDATE_ERROR_CODE = "VAL-001";

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<EmployeeValidatorErrorResponse> exception(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errorList = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                .filter(objectError -> objectError.contains(ConstraintViolation.class))
                .map(objectError -> objectError.unwrap(ConstraintViolation.class))
                .map(ConstraintViolation::getMessage).toList();
        EmployeeValidatorErrorResponse errorResponse = EmployeeValidatorErrorResponse.builder().
                errorMessage(errorList).errorCode(VALIDATE_ERROR_CODE).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> generic(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(exception.getMessage()).errorCode(GENERIC_ERROR_CODE).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
