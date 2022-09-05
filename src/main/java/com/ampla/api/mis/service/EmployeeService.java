package com.ampla.api.mis.service;

import com.ampla.api.mis.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee emp);

    void linkEmployeeToUser(Long idUser, Long idEmployer);

    List<Employee> listEmployee();

    Optional<Employee> getEmployeeById(Long id);


    void deleteEmployee(Long id);
}
