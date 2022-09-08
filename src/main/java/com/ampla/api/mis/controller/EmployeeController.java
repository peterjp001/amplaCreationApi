package com.ampla.api.mis.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.UserAlreadyExistException;
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

    @PostMapping(path = "/newEmployeeWithNoAccount")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Employee> newEmployee( @RequestBody @Valid EmployeeFunctionDTO efDTO) throws DataNotFoundException {

        return new ResponseEntity<>( employeeService.newEmployeeWithNoAccount(efDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/newEmployeeWithAccount")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseEmployeeUser> newEmployeeWithUserAccount(@Valid @RequestBody EmployeeUserDTO euDTO) throws DataNotFoundException, UserAlreadyExistException {
        return ResponseEntity.ok( employeeService.newEmployeeWithAccount(euDTO));
    }



    @GetMapping(path = "/employees")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<List<Employee>> listEmployee(){
        return ResponseEntity.ok(employeeService.listEmployee());
    }




    @GetMapping(path = "/employee/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Optional<Employee>> getOneEmployee(@PathVariable("id") Long id) throws DataNotFoundException {
        Optional<Employee> emp = employeeService.getEmployeeById(id);
        if(emp.isEmpty()){
            throw new DataNotFoundException("Employee with id "+id+" doesn't exist");
        }
        return ResponseEntity.ok(emp);
    }




    @DeleteMapping(path = "/employee/{id}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }

}
