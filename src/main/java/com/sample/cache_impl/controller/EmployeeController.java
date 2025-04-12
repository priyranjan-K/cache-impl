package com.sample.cache_impl.controller;

import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.services.EmployeeServices;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sample.cache_impl.util.Constants.*;

@RestController
@RequestMapping(value = BASE_CONTEXT_PATH)
public class EmployeeController {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeServices employeeServices;

    @Autowired
    EmployeeController(EmployeeServices employeeServices) {
        this.employeeServices = employeeServices;
    }


    @GetMapping(value = FETCH_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeRequest>> fetchAll() {
        LOGGER.info("Request received to fetch all employee details.");
        return employeeServices.fetchAllRecord();
    }

    @PostMapping(value = SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@Valid @RequestBody EmployeeRequest employeeRequest) {
        LOGGER.info("Request received to add employee details.");
        return employeeServices.addRecord(employeeRequest);
    }


    @GetMapping(value = FIND_BY_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeRequest> getByName(@RequestParam String firstName, @RequestParam String lastName) {
        LOGGER.info("Request received to update employee details.");
        return employeeServices.getRecord(EmployeePK.builder().firstName(firstName).lastName(lastName).build());
    }

    @DeleteMapping(value = DELETE_BY_FULL_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteByFullName(@RequestParam String firstName, @RequestParam String lastName) {
        LOGGER.info("Request received to delete employee details.");
        return employeeServices.deleteRecordByFullName(EmployeePK.builder().firstName(firstName).lastName(lastName).build());
    }


}
