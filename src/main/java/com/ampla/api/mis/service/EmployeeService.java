package com.ampla.api.mis.service;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.UserAlreadyExistException;
import com.ampla.api.mis.dto.EmployeeFunctionDTO;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.dto.ResponseEmployeeUser;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee emp);

    void linkEmployeeToUser(Long idUser, Long idEmployer) throws DataNotFoundException;

    List<Employee> listEmployee();

    Optional<Employee> getEmployeeById(Long id);

    void deleteEmployee(Long id);

    Employee newEmployeeWithNoAccount(EmployeeFunctionDTO emp) throws DataNotFoundException;

    ResponseEmployeeUser newEmployeeWithAccount(EmployeeUserDTO euDTO) throws UserAlreadyExistException, DataNotFoundException;


}
