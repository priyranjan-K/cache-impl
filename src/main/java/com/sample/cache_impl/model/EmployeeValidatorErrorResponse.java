package com.sample.cache_impl.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EmployeeValidatorErrorResponse {

    private List<String> errorMessage;
    private String errorCode;
}
