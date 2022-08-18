package com.ampla.api.mis.service;

import com.ampla.api.mis.entities.Employee;
import com.ampla.api.mis.entities.Status;
import com.ampla.api.mis.repository.EmployeeRepository;
import com.ampla.api.mis.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;
    private EmployeeRepository employeeRepository;

    public StatusServiceImpl(StatusRepository statusRepository, EmployeeRepository employeeRepository) {
        this.statusRepository = statusRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Status addNewStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public void addStatusToEmployee(Long idEmp, String statusName) {
        Optional<Employee> emp = employeeRepository.findById(idEmp);
        Employee employee = emp.get();
        System.out.println(employee.getFirstName());
        Status status = statusRepository.findByStatusName(statusName);

        employee.getStatus().add(status);
        employeeRepository.save(employee);
    }

    @Override
    public List<Status> listStatus() {
        return statusRepository.findAll();
    }

    @Override
    public Status getStatusByStatusName(String statusName) {
        return statusRepository.findByStatusName(statusName);
    }
}
