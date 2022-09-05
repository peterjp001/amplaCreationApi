package com.ampla.api.mis.service;


import com.ampla.api.mis.entities.Function;

import java.util.List;


public interface FunctionService {

    Function addNewStatus(Function function);

    void linkFunctionToEmployee(Long idEmp, String statusName);

    List<Function> listStatus();

    Function getFunctionByFunctionName(String statusName);
}
