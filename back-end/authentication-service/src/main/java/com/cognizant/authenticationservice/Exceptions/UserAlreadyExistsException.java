package com.cognizant.authenticationservice.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User Already Exists")
public class UserAlreadyExistsException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(){
        
    }

}