package com.sample.cache_impl.services;

import com.sample.cache_impl.exception.CustomEmployeeException;
import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.repository.EmployeeRepository;
import com.sample.cache_impl.util.ObjectToEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServicesImpl implements EmployeeServices {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeServicesImpl.class);


    private final EmployeeRepository employeeRepository;

    EmployeeServicesImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public ResponseEntity<String> updateRecord(EmployeeRequest employeeRequest) {
        Optional<EmployeeEntity> k = employeeRepository.findById(ObjectToEntityMapper.getEmployeePK(employeeRequest));
        return null;
    }

    @Override
    public ResponseEntity<String> addRecord(EmployeeRequest employeeRequest) {
        try {
            employeeRepository.save(ObjectToEntityMapper.getEmployeeEntity(employeeRequest));
            return new ResponseEntity<>("Inserted successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomEmployeeException("Unable to insert data");
        }
    }

    @Override
    public ResponseEntity<String> deleteRecordByFullName(EmployeePK employeeId) {
        employeeRepository.deleteById(employeeId);
        return null;
    }

    @Override
    public ResponseEntity<List<EmployeeRequest>> fetchAllRecord() {
        try {
            //return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}
