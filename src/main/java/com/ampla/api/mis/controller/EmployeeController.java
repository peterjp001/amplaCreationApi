package com.ampla.api.mis.controller;

import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.security.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    protected static final Log logger = LogFactory.getLog(EmployeeController.class);

    private EmployeeService empService;
    private AccountService accountService;

    public EmployeeController(EmployeeService empService) {
        this.empService = empService;
    }

    @GetMapping(path="/employees")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Employee> getEmployees(){
        logger.info("---getEmployees method Called");
        return empService.listEmployee();
    }

    @PostMapping(path="/employees")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Employee addEmployee(@RequestBody Employee emp){
        return empService.saveEmployee(emp);
    }

    @PostMapping(path="/linkEmployeeToUser")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void setUserToEmployee(@RequestBody EmployeeUserDTO employeeUserDTO){
        empService.linkEmployeeToUser(employeeUserDTO.getIdUser(), employeeUserDTO.getIdEmployer());
    }


    @GetMapping(path="/employees/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public Optional<Employee> getEmployee(@PathVariable Long id){
        return empService.getEmployeeById(id);
    }

}

