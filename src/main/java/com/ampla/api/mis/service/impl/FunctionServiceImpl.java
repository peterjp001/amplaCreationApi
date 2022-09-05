package com.ampla.api.mis.service.impl;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.repository.FunctionRepository;
import com.ampla.api.mis.service.FunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;
    private final EmployeeRepository employeeRepository;

    public FunctionServiceImpl(FunctionRepository functionRepository, EmployeeRepository employeeRepository) {
        this.functionRepository = functionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Function addNewStatus(Function function) {
        return functionRepository.save(function);
    }

    @Override
    public void linkFunctionToEmployee(Long idEmp, String functionName) {
        Optional<Employee> emp = employeeRepository.findById(idEmp);
        if(emp.isPresent()){

            Employee employee = emp.get();
            Function function = functionRepository.findByFunctionName(functionName);

            employee.getFunctions().add(function);
            employeeRepository.save(employee);

        }

    }

    @Override
    public List<Function> listStatus() {
        return functionRepository.findAll();
    }

    @Override
    public Function getFunctionByFunctionName(String statusName) {
        return functionRepository.findByFunctionName(statusName);
    }
}
