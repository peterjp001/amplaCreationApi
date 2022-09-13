package com.ampla.api.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends InternalAuthenticationServiceException {

    public UserAuthenticationException(String message){
        super(message);
    }
}
