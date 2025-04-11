package com.sample.cache_impl.repository;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, EmployeePK> {

}
