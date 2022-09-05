package com.ampla.api.mis.service.impl;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.service.EmployeeService;
import com.ampla.api.security.entities.User;
import com.ampla.api.security.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository emplRepo;
    private final UserRepository userRepository;


    public EmployeeServiceImpl(EmployeeRepository emplRepo, UserRepository userRepository ) {
        this.emplRepo = emplRepo;
        this.userRepository = userRepository;
    }

    @Override
    public Employee saveEmployee(Employee emp) {
        return emplRepo.save(emp);
    }

    @Override
    public void linkEmployeeToUser(Long idUser, Long idEmployer) {
         Optional<User> user = userRepository.findById(idUser);
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
    public void deleteEmployee(Long id) {
        emplRepo.deleteById(id);
    }
}
