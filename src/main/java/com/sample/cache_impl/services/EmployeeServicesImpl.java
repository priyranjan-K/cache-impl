package com.sample.cache_impl.services;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.repository.EmployeeRepository;
import com.sample.cache_impl.util.EntityToObjectMapper;
import com.sample.cache_impl.util.ObjectToEntityMapper;
import com.sample.cache_impl.util.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sample.cache_impl.exception.CustomEmployeeException.*;
import static com.sample.cache_impl.util.ResponseGenerator.*;

@Service
public class EmployeeServicesImpl implements EmployeeServices {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeServicesImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    EmployeeServicesImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public ResponseEntity<String> updateRecord(EmployeeRequest employeeRequest) {
        try {
            LOGGER.info("Please Wait!...updating record");
            EmployeeEntity employeeEntity = employeeRepository.findById(ObjectToEntityMapper.getEmployeePK(employeeRequest)).orElse(null);
            if (employeeEntity != null) {
                employeeRepository.save(ObjectToEntityMapper.getEmployeeEntity(employeeRequest));
            }
            return UPDATE_SUCCESS_MESSAGE;
        } catch (Exception e) {
            throw CUSTOM_UPDATE_ERROR;
        }
    }

    @Override
    public ResponseEntity<String> addRecord(EmployeeRequest employeeRequest) {
        try {
            LOGGER.info("Please Wait!...adding record");
            employeeRepository.save(ObjectToEntityMapper.getEmployeeEntity(employeeRequest));
            return INSERT_SUCCESS_MESSAGE;
        } catch (Exception e) {
            throw CUSTOM_INSERT_ERROR;
        }
    }

    @Override
    public ResponseEntity<String> deleteRecordByFullName(EmployeePK employeeId) {
        try {
            LOGGER.info("Please Wait!...deleting record");
            EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
            if (employeeEntity != null) {
                employeeRepository.deleteById(employeeId);
                return DELETE_SUCCESS_MESSAGE;
            } else {
                LOGGER.info("Opps! Record does not exist.");
                return RECORD_NOT_FOUND_MESSAGE;
            }

        } catch (Exception e) {
            throw CUSTOM_DELETE_ERROR;
        }
    }

    @Override
    public ResponseEntity<List<EmployeeRequest>> fetchAllRecord() {
        try {
            LOGGER.info("Please Wait!...fetching all employee record");
            List<EmployeeEntity> k = employeeRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).
                    headers(ResponseGenerator.getHeader()).body(EntityToObjectMapper.getEmployeeRequest(employeeRepository.findAll()));
        } catch (Exception e) {
            throw CUSTOM_FETCH_ERROR;
        }

    }
}
