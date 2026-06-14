package com.sample.cache_impl.controller;

import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.services.EmployeeServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeServices employeeServices;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeRequest employeeRequest;

    @BeforeEach
    void setUp() {
        employeeRequest = EmployeeRequest.builder().firstName("John").lastName("Doe").build();
    }

    @Test
    void fetchAll() {
        // Arrange
        when(employeeServices.fetchAllRecord()).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        // Act
        ResponseEntity<List<EmployeeRequest>> response = employeeController.fetchAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
        verify(employeeServices, times(1)).fetchAllRecord();
    }

    @Test
    void add() {
        // Arrange
        when(employeeServices.addRecord(any(EmployeeRequest.class))).thenReturn(ResponseEntity.ok("Success"));

        // Act
        ResponseEntity<String> response = employeeController.add(employeeRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(employeeServices, times(1)).addRecord(any(EmployeeRequest.class));
    }

    @Test
    void getByName() {
        // Arrange
        when(employeeServices.getRecord(any(EmployeePK.class))).thenReturn(ResponseEntity.ok(employeeRequest));

        // Act
        ResponseEntity<EmployeeRequest> response = employeeController.getByName("John", "Doe");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeRequest, response.getBody());
        verify(employeeServices, times(1)).getRecord(any(EmployeePK.class));
    }

    @Test
    void deleteByFullName() {
        // Arrange
        when(employeeServices.deleteRecordByFullName(any(EmployeePK.class))).thenReturn(ResponseEntity.ok("Success"));

        // Act
        ResponseEntity<String> response = employeeController.deleteByFullName("John", "Doe");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(employeeServices, times(1)).deleteRecordByFullName(any(EmployeePK.class));
    }

    @Test
    void clearCache() {
        // Arrange
        when(employeeServices.crearRedisEntries()).thenReturn(ResponseEntity.ok("Success"));

        // Act
        ResponseEntity<String> response = employeeController.clearCache();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(employeeServices, times(1)).crearRedisEntries();
    }
}
