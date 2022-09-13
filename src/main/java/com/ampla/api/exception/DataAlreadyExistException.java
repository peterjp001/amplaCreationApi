package com.ampla.api.exception;

import javax.persistence.NonUniqueResultException;

public class DataAlreadyExistException extends NonUniqueResultException {

    public DataAlreadyExistException(String message){
        super(message);
    }
}
