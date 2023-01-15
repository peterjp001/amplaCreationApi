package com.ampla.api.mis.service;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.EmployeeAsTeacherDTO;
import com.ampla.api.mis.dto.EmployeeFunctionDTO;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.dto.ResponseEmployeeUser;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    String createCodeEmployee(String firstName, String lastName,  String phone);

    Employee getByPhone(String phone);

    Employee getByFirstNameAndLastName(String firstName, String lastName) throws DataNotFoundException;

//    Boolean testPhone(String phone);
    Employee saveEmployee(Employee emp) throws DataAlreadyExistException;

//    void linkEmployeeToUser(Long idUser, Long idEmployer) throws DataNotFoundException;

    List<Employee> listEmployee();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Long id, EmployeeFunctionDTO euDTO) throws DataNotFoundException, DataAlreadyExistException;

    void deleteEmployee(Long id);

    Employee newEmployeeWithNoAccount(EmployeeFunctionDTO emp) throws DataNotFoundException, DataAlreadyExistException;

    ResponseEmployeeUser newEmployeeWithAccount(EmployeeUserDTO euDTO) throws DataAlreadyExistException, DataNotFoundException;

    User userToExistingEmployee(User user, String codeEmployee) throws DataNotFoundException;

    List<ResponseEmployeeUser> allEmployeeWithUserAccount();


    void addFunctionToEmployee(Long idEmployee, String functionName) throws DataNotFoundException;

    void removeFunctionToEmployee(Long idEmployee, String functionName) throws DataNotFoundException;

    EmployeeAsTeacherDTO addTeacher(EmployeeAsTeacherDTO eatDTO) throws DataNotFoundException;

    List<Employee> listEmployeeByFunctionName(String functionName);

    List<Employee> listEmployeeByCourseName(String courseName);


    Employee getEmployeeByCode(String codeEmployee) throws DataNotFoundException;

    List<Employee> listEmployeeByCourseId(Long id);
}
