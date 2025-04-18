package com.sample.cache_impl.services;

import com.sample.cache_impl.cache.EmployeeCacheService;
import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.util.EntityToObjectMapper;
import com.sample.cache_impl.util.ResponseGenerator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sample.cache_impl.exception.CustomEmployeeException.*;
import static com.sample.cache_impl.util.ResponseGenerator.*;
import static com.sample.cache_impl.util.ResponseGenerator.CUSTOM_REDIS_MESSAGED;

@Service
public class EmployeeServicesImpl implements EmployeeServices {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeServicesImpl.class);

    private final EmployeeCacheService employeeCacheService;

    @Autowired
    private EmployeeServicesImpl(EmployeeCacheService employeeCacheService) {
        this.employeeCacheService = employeeCacheService;
    }


    @Override
    public ResponseEntity<EmployeeRequest> getRecord(EmployeePK employeeId) {
        try {
            LOGGER.info("Please Wait!...fetching record");
            EmployeeEntity employeeEntity = employeeCacheService.findByPK(employeeId);
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
            throw CUSTOM_FETCH_ERROR;
        }
    }

    @Override
    public ResponseEntity<String> addRecord(EmployeeRequest employeeRequest) {
        try {
            LOGGER.info("Please Wait!...adding record");
            employeeCacheService.save(employeeRequest);
            return INSERT_SUCCESS_MESSAGE;
        } catch (Exception e) {
            LOGGER.error("Error occurred while inserting the record with error message = {}", e.getMessage());
            throw CUSTOM_INSERT_ERROR;
        }
    }

    @Override
    public ResponseEntity<String> deleteRecordByFullName(EmployeePK employeeId) {
        try {
            LOGGER.info("Please Wait!...deleting record");
            EmployeeEntity employeeEntity = employeeCacheService.findByPK(employeeId);
            if (employeeEntity != null) {
                employeeCacheService.deleteByPK(employeeId);
                return DELETE_SUCCESS_MESSAGE;
            } else {
                LOGGER.info("Opps! Unable to delete, Since record does not exist.");
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
                    headers(ResponseGenerator.getHeader()).body(EntityToObjectMapper.getEmployeeRequestList(employeeCacheService.getAll()));
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching the record with error message = {}", e.getMessage());
            throw CUSTOM_FETCH_ERROR;
        }

    }

    @Override
    public ResponseEntity<String> crearRedisEntries() {
        try {
            LOGGER.info("Please Wait!...clearing the all redis entries");
            employeeCacheService.crearRedisEntries();
            return CUSTOM_REDIS_MESSAGED;
        } catch (Exception e) {
            throw CUSTOM_REDIS_ERROR;
        }
    }
}
