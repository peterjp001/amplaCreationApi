package com.ampla.api.mis.service.impl;

import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.mis.dto.EmployeeFunctionDTO;
import com.ampla.api.mis.dto.EmployeeUserDTO;
import com.ampla.api.mis.dto.ResponseEmployeeUser;
import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Function;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.repository.FunctionRepository;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository emplRepo;
    private final AccountService accountService;

    private final FunctionRepository functionRepository;


    public EmployeeServiceImpl(EmployeeRepository emplRepo, AccountService accountService1, FunctionRepository functionRepository) {
        this.emplRepo = emplRepo;
        this.accountService = accountService1;
        this.functionRepository = functionRepository;
    }

    @Override
    public Employee saveEmployee(Employee emp) {
        return emplRepo.save(emp);
    }

    @Override
    public void linkEmployeeToUser(Long idUser, Long idEmployer) throws DataNotFoundException {
        Optional<User> user = accountService.getUserById(idUser);
        Optional<Employee> emp = emplRepo.findById(idEmployer);
        if (user.isPresent() && emp.isPresent()) {
            User u = user.get();
            Employee e = emp.get();
            e.setUser(u);
            System.out.println(u.getEmployee());
            emplRepo.save(e);
        }
    }


    @Override
    public List<Employee> listEmployee() {
        return emplRepo.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return emplRepo.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeFunctionDTO efDTO) throws DataNotFoundException{

        Optional<Employee> emp = this.getEmployeeById(id);

        if(emp.isEmpty()){
            throw  new DataNotFoundException("Employee with id "+id+ " not found");
        }
        Employee updateEmp = emp.get();

        if(efDTO.getFirstName() != null){
            updateEmp.setFirstName(efDTO.getFirstName());
        }
        if (efDTO.getLastName() != null){
            updateEmp.setLastName(efDTO.getLastName());
        }
        if(efDTO.getSexe() != null){
            updateEmp.setSexe(efDTO.getSexe());
        }
        if(efDTO.getNif() != null){
            updateEmp.setNif(efDTO.getNif());
        }
        if(efDTO.getEmail() != null){
            updateEmp.setEmail(efDTO.getEmail());
        }
        if(efDTO.getPhone() != null){
            updateEmp.setPhone(efDTO.getPhone());
        }
        if(efDTO.getBirthDate() != null){
            updateEmp.setBirthDate(efDTO.getBirthDate());
        }

        return emplRepo.save(updateEmp);
    }

    @Override
    public void deleteEmployee(Long id) {
        emplRepo.deleteById(id);
    }

    @Override
    public Employee newEmployeeWithNoAccount(EmployeeFunctionDTO euDTO) throws DataNotFoundException {

        Employee emp = new Employee();

        emp.setCodeEmployee(euDTO.getCodeEmployee());
        emp.setFirstName(euDTO.getFirstName());
        emp.setLastName(euDTO.getLastName());
        emp.setSexe(euDTO.getSexe());
        emp.setEmail(euDTO.getEmail());
        emp.setPhone(euDTO.getPhone());
        emp.setBirthDate(euDTO.getBirthDate());
        emp.setNif(euDTO.getNif());

        if(euDTO.getFunctions().size() >0){
            euDTO.getFunctions().forEach(function -> {
                Function func = functionRepository.findByFunctionName(function.getFunctionName());
                emp.getFunctions().add(func);
            });
        }else{
            throw new DataNotFoundException("Function Required");
        }

        return saveEmployee(emp);
    }

    @Override
    public ResponseEmployeeUser newEmployeeWithAccount(EmployeeUserDTO euDTO) throws DataAlreadyExistException, DataNotFoundException {
        User u = new User();
        Employee emp = new Employee();

        u.setUsername(euDTO.getUsername());
        u.setPassword(euDTO.getPassword());
        u.setRoles(euDTO.getRoles());
        User newUser = accountService.addNewUser(u);

        emp.setCodeEmployee(euDTO.getCodeEmployee());
        emp.setFirstName(euDTO.getFirstName());
        emp.setLastName(euDTO.getLastName());
        emp.setSexe(euDTO.getSexe());
        emp.setEmail(euDTO.getEmail());
        emp.setPhone(euDTO.getPhone());
        emp.setBirthDate(euDTO.getBirthDate());
        emp.setNif(euDTO.getNif());
        emp.setUser(newUser);
        Employee newEmp = emplRepo.save(emp);
        if (euDTO.getFunctions().size() > 0) {
            euDTO.getFunctions().forEach(function -> {
                Function func = functionRepository.findByFunctionName(function.getFunctionName());
                newEmp.getFunctions().add(func);
            });
        }

        return new ResponseEmployeeUser(newEmp, newUser);
    }
}
