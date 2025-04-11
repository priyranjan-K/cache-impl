package com.sample.cache_impl.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private String errorMessage;
    private String errorCode;
}
