package product.spring.boot.autoconfigure.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Supplier Service Not Working")
public class SupplierServiceNotWorkingException  extends Exception{

    private static final long serialVersionUID = 1L;
    
    public SupplierServiceNotWorkingException(){
        
    }

    
}

