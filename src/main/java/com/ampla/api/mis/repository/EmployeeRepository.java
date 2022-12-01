package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhone(String phone);
    Employee findByEmail(String email);

    Employee findByNif(String nif);
    Employee findEmployeeByUser(User user);

    Employee findEmployeeByCodeEmployee(String codeEmployee);

    List<Employee> findEmployeeByFunctionsFunctionName(String functionName);

    List<Employee> findEmployeeByCourseCourseName(String courseName);

    List<Employee> findEmployeeByCourseId(Long id);
}
