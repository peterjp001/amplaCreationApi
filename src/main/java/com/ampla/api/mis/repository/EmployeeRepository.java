package com.ampla.api.mis.repository;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByPhone(String phone);


    Employee findEmployeeByUser(User user);

}
