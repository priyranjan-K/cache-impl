package com.sample.cache_impl.util;

import com.sample.cache_impl.exception.CustomEmployeeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.sample.cache_impl.util.Constants.*;

@Component
public class ResponseGenerator {

    public static final ResponseEntity<String> UPDATE_SUCCESS_MESSAGE = ResponseEntity.status(HttpStatus.OK).headers(getHeader()).body(UPDATED_SUCCESSFULLY);
    public static final ResponseEntity<String> DELETE_SUCCESS_MESSAGE = ResponseEntity.status(HttpStatus.OK).headers(getHeader()).body(DELETED_SUCCESSFULLY);
    public static final ResponseEntity<String> INSERT_SUCCESS_MESSAGE = ResponseEntity.status(HttpStatus.CREATED).headers(getHeader()).body(INSERTED_SUCCESSFULLY);
    public static final ResponseEntity<String> RECORD_NOT_FOUND_MESSAGE = ResponseEntity.status(HttpStatus.NOT_FOUND).headers(getHeader()).body(RECORD_NOT_FOUND);
    public static final ResponseEntity<String> CUSTOM_REDIS_MESSAGED = ResponseEntity.status(HttpStatus.OK).headers(getHeader()).body(REDIS_CLEAN_MESSAGE);


    public static HttpHeaders getHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
