package com.cognizant.supplierservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product doesn't exist")
public class ProductNotExistsException  extends Exception{

    private static final long serialVersionUID = 1L;
    
    public ProductNotExistsException(){
        
    }

    
}

