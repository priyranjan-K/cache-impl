package com.sample.cache_impl.util;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.model.EmployeeRequest;
import org.springframework.stereotype.Component;

@Component
public class EntityToObjectMapper {

    public static EmployeePK getEmployeePK(EmployeeRequest employeeRequest) {
        return EmployeePK.builder().firstName(employeeRequest.getFirstName()).lastName(employeeRequest.getLastName()).build();
    }


    public static EmployeeEntity getEmployeeEntity(EmployeeRequest employeeRequest) {
        EmployeePK pk = getEmployeePK(employeeRequest);
        return EmployeeEntity.builder().employeePK(pk).dob(employeeRequest.getDob()).age(employeeRequest.getAge()).grade(
                employeeRequest.getGrade()).build();
    }

    public static EmployeeRequest getEmployeeRequest(EmployeeEntity employeeEntity) {
        return EmployeeRequest.builder().
                firstName(employeeEntity.getEmployeePK().getFirstName()).
                lastName(employeeEntity.getEmployeePK().getLastName()).
                age(employeeEntity.getAge()).grade(employeeEntity.getGrade()).
                dob(employeeEntity.getDob()).build();
    }


}
