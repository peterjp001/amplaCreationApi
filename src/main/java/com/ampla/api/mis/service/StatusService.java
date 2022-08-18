package com.ampla.api.mis.service;


import com.ampla.api.mis.entities.Status;
import org.springframework.stereotype.Service;

import java.util.List;


public interface StatusService {

    Status addNewStatus(Status status);

    void addStatusToEmployee(Long idEmp, String statusName);

    List<Status> listStatus();

    Status getStatusByStatusName(String statusName);
}
