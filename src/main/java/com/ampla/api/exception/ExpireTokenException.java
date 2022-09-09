package com.ampla.api.exception;


import javax.servlet.ServletException;
import java.io.IOException;

public class ExpireTokenException extends ServletException {

    public ExpireTokenException(String message){
        super(message);
    }
}
