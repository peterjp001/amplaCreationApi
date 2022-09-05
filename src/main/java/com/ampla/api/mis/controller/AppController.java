package com.ampla.api.mis.controller;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.mis.service.FunctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class AppController {

    private final EmployeeService employeeService;
    private final FunctionService functionService;

    public AppController(EmployeeService employeeService, FunctionService functionService) {
        this.employeeService = employeeService;
        this.functionService = functionService;
    }

    @PostMapping(path = "/newemployee")
    @PostAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<Employee> newEmployee( @RequestBody @Valid EmployeeUserDTO euDTO){
        Employee emp = new Employee();
        emp.setFirstName(euDTO.getFirstName());
        emp.setLastName(euDTO.getLastName());
        emp.setSexe(euDTO.getSexe());
        emp.setEmail(euDTO.getEmail());
        emp.setPhone(euDTO.getPhone());
        emp.setBirthDate(euDTO.getBirthDate());
        emp.setNif(euDTO.getNif());
        Employee newEmp = employeeService.saveEmployee(emp);
        euDTO.getFunction().forEach(function -> {
            Function func = functionService.getFunctionByFunctionName(function.getFunctionName());
            newEmp.getFunctions().add(func);
        });
        return new ResponseEntity<>( employeeService.saveEmployee(newEmp), HttpStatus.CREATED);
    }

    @PostMapping(path = "/newEmployeeWithUserAccount")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<EmployeeUserDTO> newEmployeeWithUserAccount(@Valid @RequestBody EmployeeUserDTO euDTO){
        return ResponseEntity.ok(euDTO);
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
