package com.ampla.api.mis.service;


import com.ampla.api.exception.DataAlreadyExistException;
import com.ampla.api.exception.DataNotFoundException;
import com.ampla.api.mis.entities.Function;

import java.util.List;


public interface FunctionService {

    Function addNewFunction(Function function) throws DataAlreadyExistException;

    void linkFunctionToEmployee(Long idEmp, String statusName);

    List<Function> listFunction();

    Function getFunctionByFunctionName(String statusName);

    Function updateFunction(Long id,Function function) throws DataNotFoundException;
}
