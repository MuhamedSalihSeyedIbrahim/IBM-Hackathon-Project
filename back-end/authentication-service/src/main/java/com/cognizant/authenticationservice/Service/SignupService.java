package com.cognizant.authenticationservice.Service;

import com.cognizant.authenticationservice.Dao.DaoOperation;
import com.cognizant.authenticationservice.Exceptions.UserAlreadyExistsException;
import com.cognizant.authenticationservice.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SignupService
 */
@Service
@Slf4j
public class SignupService {

    @Autowired
    DaoOperation daoOperation;

    @Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.SupplierService.addSupplier}")
    private String addSupplierUri; 
    
    @Autowired
    PasswordEncoder passwordEncoder;

    public String signup(User user, String header) throws UserAlreadyExistsException {

        if(user.getUserType() == "supplier") {
            HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", header);
        log.info("header"+header);
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        log.info("entity"+entity);
        String message = restTemplate.exchange(addSupplierUri, HttpMethod.POST, entity, String.class).getBody();
                log.info("message"+message);
        }
       return daoOperation.signup(user, this.passwordEncoder) ?"Signup Success" : "Signup Failed";
    }

}