package com.sample.cache_impl.services;

import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeServices {

    ResponseEntity<EmployeeRequest> getRecord(EmployeePK employeeId);

    ResponseEntity<String> addRecord(EmployeeRequest employeeRequest);

    ResponseEntity<String> deleteRecordByFullName(EmployeePK employeeId);

    ResponseEntity<List<EmployeeRequest>> fetchAllRecord();

    ResponseEntity<String> crearRedisEntries();

}
