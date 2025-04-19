package com.sample.cache_impl.cache;

import com.sample.cache_impl.model.EmployeeEntity;
import com.sample.cache_impl.model.EmployeePK;
import com.sample.cache_impl.model.EmployeeRequest;
import com.sample.cache_impl.repository.EmployeeRepository;
import com.sample.cache_impl.util.ObjectToEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeCacheService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    EmployeeCacheService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @CachePut(value = "employeeCache", key = "#employeeRequest.firstName + '_' + #employeeRequest.lastName")
    public EmployeeEntity save(EmployeeRequest employeeRequest) {
        return employeeRepository.save(ObjectToEntityMapper.getEmployeeEntity(employeeRequest));
    }

    @CacheEvict(value = "employeeCache", key = "#employeePK.firstName + '_' + #employeePK.lastName")
    public void deleteByPK(EmployeePK employeePK) {
        employeeRepository.deleteById(employeePK);
    }

    @Cacheable(value = "employeeCache", key = "#employeePK.firstName + '_' + #employeePK.lastName")
    public EmployeeEntity findByPK(EmployeePK employeePK) {
        return employeeRepository.findById(employeePK).orElse(null);
    }

    public List<EmployeeEntity> getAll() {
        return employeeRepository.findAll();
    }


    @CacheEvict(value = "employeeCache", allEntries = true)
    public void crearRedisEntries() {
    }
}
