package com.sample.cache_impl.util;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.model.EmployeeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityToObjectMapper {

    public static EmployeeRequest getEmployeeRequest(EmployeeEntity employeeEntity) {
        EmployeePK pk = employeeEntity.getEmployeePK();
        return EmployeeRequest.builder().firstName(pk.getFirstName()).lastName(pk.getLastName()).
                age(employeeEntity.getAge()).dob(employeeEntity.getDob()).
                grade(employeeEntity.getGrade()).createdDate(employeeEntity.getCreatedDate()).
                lastModifiedDate(employeeEntity.getLastModifiedDate()).build();
    }


    public static List<EmployeeRequest> getEmployeeRequestList(List<EmployeeEntity> employeeEntityList) {
        List<EmployeeRequest> list = new ArrayList<>();
        for (EmployeeEntity e : employeeEntityList) {
            list.add(getEmployeeRequest(e));
        }
        return list;
    }


}
