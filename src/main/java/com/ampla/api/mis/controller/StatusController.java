package com.ampla.api.mis.controller;


import com.ampla.api.mis.dto.EmployeeStatusDTO;
import com.ampla.api.mis.entities.Status;
import com.ampla.api.mis.service.StatusService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatusController {

    private StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping(path="/status")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public List<Status> getAllStatus(){
        return statusService.listStatus();
    }

    @GetMapping(path="/status/{statusName}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Status getOneStatus(@PathVariable  String statusName){
        return statusService.getStatusByStatusName(statusName);
    }

    @PostMapping(path="/status")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Status saveStatus(@RequestBody Status status){
        return statusService.addNewStatus(status);
    }

    @PostMapping(path="/linkStatusToEmployee")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void statusToUser(@RequestBody EmployeeStatusDTO employeeStatusDTO ){
        statusService.addStatusToEmployee(employeeStatusDTO.getIdEmp(), employeeStatusDTO.getStatusName());

    }

}
