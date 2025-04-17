package com.sample.cache_impl.services;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.repository.EmployeeRepository;
import com.sample.cache_impl.util.EntityToObjectMapper;
import com.sample.cache_impl.util.ObjectToEntityMapper;
import com.sample.cache_impl.util.ResponseGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Cacheable(value = "employeeCache", key = "#employeeId")
    public ResponseEntity<EmployeeRequest> getRecord(EmployeePK employeeId) {
        try {
            LOGGER.info("Please Wait!...fetching record");
            EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
            if (employeeEntity != null) {
                return ResponseEntity.status(HttpStatus.OK).
                        headers(ResponseGenerator.getHeader()).body(EntityToObjectMapper.getEmployeeRequest(employeeEntity));
            } else {
                LOGGER.info("Opps! Record does not exist.");
                throw new EntityNotFoundException("Record not found for the firstName = "
                        + employeeId.getFirstName() + " and LastName = " + employeeId.getLastName());
            }
        } catch (EntityNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching entity with error message = {}", e.getMessage());
            throw CUSTOM_UPDATE_ERROR;
        }
    }

    @Override
    @CachePut(value = "employeeCache", key = "#employeeRequest.firstName + '_' + #employeeRequest.lastName")
    public ResponseEntity<String> addRecord(EmployeeRequest employeeRequest) {
        try {
            LOGGER.info("Please Wait!...adding record");
            employeeRepository.save(ObjectToEntityMapper.getEmployeeEntity(employeeRequest));
            return INSERT_SUCCESS_MESSAGE;
        } catch (Exception e) {
            LOGGER.error("Error occurred while inserting the record with error message = {}", e.getMessage());
            throw CUSTOM_INSERT_ERROR;
        }
    }

    @Override
    @CacheEvict(value = "employeeCache", key = "#employeeId.firstName + '_' + #employeeId.lastName")
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
            LOGGER.error("Error occurred while deleting the record with error message = {}", e.getMessage());
            throw CUSTOM_DELETE_ERROR;
        }
    }

    @Override
    public ResponseEntity<List<EmployeeRequest>> fetchAllRecord() {
        try {
            LOGGER.info("Please Wait!...fetching all employee record");
            return ResponseEntity.status(HttpStatus.OK).
                    headers(ResponseGenerator.getHeader()).body(EntityToObjectMapper.getEmployeeRequestList(employeeRepository.findAll()));
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching the record with error message = {}", e.getMessage());
            throw CUSTOM_FETCH_ERROR;
        }

    }
}
