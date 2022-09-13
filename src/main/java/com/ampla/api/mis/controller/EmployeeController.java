package com.ampla.api.mis.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.mis.dto.EmployeeFunctionDTO;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.dto.ResponseEmployeeUser;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.mis.service.FunctionService;
import com.ampla.api.security.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final FunctionService functionService;

    public EmployeeController(EmployeeService employeeService, FunctionService functionService) {
        this.employeeService = employeeService;
        this.functionService = functionService;
    }


//    CREATE EMPLOYEE WITH NO USER ACCOUNT
    @PostMapping(path = "/newEmployeeWithNoAccount")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Employee> newEmployee(@RequestBody @Valid EmployeeFunctionDTO efDTO) throws DataNotFoundException, DataAlreadyExistException {

        return new ResponseEntity<>(employeeService.newEmployeeWithNoAccount(efDTO), HttpStatus.CREATED);
    }


    //    CREATE EMPLOYEE WITH USER ACCOUNT
    @PostMapping(path = "/newEmployeeWithAccount")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseEmployeeUser> newEmployeeWithUserAccount(@Valid @RequestBody EmployeeUserDTO euDTO) throws DataNotFoundException, DataAlreadyExistException {
        return ResponseEntity.ok(employeeService.newEmployeeWithAccount(euDTO));
    }

//    ADD USER TO AN EXISTING EMPLOYEE
    @PostMapping(path = "/usertoemployee/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseEmployeeUser> newUserToEmployee(@RequestBody @Valid User user, @PathVariable("id") Long idEmployee) throws DataNotFoundException {
        return ResponseEntity.ok(employeeService.userToExistingEmployee(user,idEmployee));
    }


//    GET ALL EMPLOYEES DATA
    @GetMapping(path = "/employees")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<List<Employee>> listEmployee() {
        return ResponseEntity.ok(employeeService.listEmployee());
    }


//    GET DATA OF A UNIQUE EMPLOYEE BY HIS ID
    @GetMapping(path = "/employee/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Optional<Employee>> getOneEmployee(@PathVariable("id") Long id) throws DataNotFoundException {
        Optional<Employee> emp = employeeService.getEmployeeById(id);
        if (emp.isEmpty()) {
            throw new DataNotFoundException("Employee with id " + id + " doesn't exist");
        }
        return ResponseEntity.ok(emp);
    }


//    DELETE EMPLOYEE DATA. THIS FUNCTIONALITY IS EXECUTE IN CASCADE
//    AND AFFECTING EMPLOYEE,FUNCTION,USER AND ROLE RELATIONSHIP
    @DeleteMapping(path = "/employee/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
    }


//    UPDATE EMPLOYEE DATA BY HIS ID
    @PutMapping(path = "/employee/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeFunctionDTO efDTO) throws DataNotFoundException, DataAlreadyExistException {
        return new ResponseEntity<>(employeeService.updateEmployee(id, efDTO), HttpStatus.OK);
    }


//    CREATE A NEW EMPLOYEE FUNCTION
    @PostMapping(path = "/newfunction")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Function newFunction(@RequestBody @Valid Function function) throws DataAlreadyExistException {
        return functionService.addNewFunction(function);
    }


//    GET ALL EMPLOYEE FUNCTIONS
    @GetMapping(path = "/functions")
    @PostAuthorize("hasAnyAuthority('USER')")
    public List<Function> listFunction() {
        return functionService.listFunction();
    }


//    UPDATE EMPLOYEE FUNCTION BY THE ID FUNCTION
    @PutMapping(path = "/function/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Function updateFunction(@PathVariable("id") Long id, @RequestBody Function function) throws DataNotFoundException {
        return functionService.updateFunction(id, function);
    }


//    LINK EMPLOYEE TO ONE OR MANY FUNCTION(S)
    @PostMapping(path = "/addfunctiontoemployee/employee/{id}/function/{functionName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void addfunctiontoemployee(@PathVariable("id") Long id, @PathVariable("functionName") String functionName) throws DataNotFoundException {
        employeeService.addFunctionToEmployee(id, functionName);
    }

    //    UNLINK EMPLOYEE TO ONE OR MANY FUNCTION(S)
    @PostMapping(path = "/removefunctiontoemployee/employee/{id}/function/{functionName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void removefunctiontoemployee(@PathVariable("id") Long id, @PathVariable("functionName") String functionName) throws DataNotFoundException {
        employeeService.removeFunctionToEmployee(id, functionName);
    }

    @GetMapping(path = "/employeewithuser")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public List<ResponseEmployeeUser> employeeWithUserAccount(){
        return employeeService.allEmployeeWithUserAccount();
    }

}
