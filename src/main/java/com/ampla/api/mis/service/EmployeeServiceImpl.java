package com.ampla.api.mis.service;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.security.entities.AppUser;
import com.ampla.api.security.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository emplRepo;
    private AppUserRepository userRepository;

    public EmployeeServiceImpl(EmployeeRepository emplRepo, AppUserRepository userRepository) {
        this.emplRepo = emplRepo;
        this.userRepository = userRepository;
    }

    @Override
    public Employee saveEmployee(Employee emp) {
        return emplRepo.save(emp);
    }

    @Override
    public void linkEmployeeToUser(Long idUser, Long idEmployer) {
         Optional<AppUser> user = userRepository.findById(idUser);
         Optional<Employee> emp = emplRepo.findById(idEmployer);
        if (user.isPresent() && emp.isPresent()) {
            AppUser u = user.get();
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
}
