package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
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
    public Function addNewFunction(Function function) throws DataAlreadyExistException {
        Optional<Function> func = Optional.ofNullable(functionRepository.findByFunctionName(function.getFunctionName()));
        if(func.isPresent()){
            throw new DataAlreadyExistException("Function "+ function.getFunctionName()+ " already exist");
        }
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
    public List<Function> listFunction() {
        return functionRepository.findAll();
    }

    @Override
    public Function getFunctionByFunctionName(String statusName) {
        return functionRepository.findByFunctionName(statusName);
    }

    @Override
    public Function updateFunction(Long id, Function function) throws DataNotFoundException {
        Optional<Function> func = functionRepository.findById(id);
        if (func.isEmpty()){
            throw new DataNotFoundException("Function with id "+ id+ " not found");
        }
        Function updateFunc = func.get();
        updateFunc.setFunctionName(function.getFunctionName());

        return functionRepository.save(updateFunc);
    }
}
