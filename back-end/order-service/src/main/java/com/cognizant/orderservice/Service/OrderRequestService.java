package com.cognizant.orderservice.Service;

import java.util.ArrayList;

import com.cognizant.orderservice.Model.Coordinates;
import com.cognizant.orderservice.Model.CustomerModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderRequestService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.AuthenticationService.getSupplierUri}")
    private String supplierRequestUri;

    @Value("${com.cognizant.AuthenticationService.getCoordinateUri}")
    private String coordinateRequestUri;

    @Value("${com.cognizant.AuthenticationService.getCustomerUri}")
    private String customerRequestUri;

    @Value("${com.cognizant.AuthenticationService.getAdminUri}")
    private String adminRequestUri;

    public String getSupplier(String customerId, String headers) {

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization", headers);
        HttpEntity<String> entity = new HttpEntity<String>(null,header);
        ArrayList<String> supplier = restTemplate.exchange(supplierRequestUri, HttpMethod.GET, entity, new ParameterizedTypeReference<ArrayList<String>>(){}, customerId)
                .getBody();
        log.info("supplier"+supplier);
        String supplierId = supplier.get(0);
        log.info("id"+supplierId);
        return supplierId;
    }

    public Coordinates getCustomerCoordinate(String customerId, String header){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        HttpEntity<Coordinates> entity = new HttpEntity<Coordinates>(null,headers);
        Coordinates coordinate = restTemplate.exchange(coordinateRequestUri, HttpMethod.GET, entity, Coordinates.class, customerId)
                .getBody();
                log.info("coordinate"+coordinate);
        return coordinate;
    }

    public CustomerModel getCustomer(String customerId, String header){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        HttpEntity<CustomerModel> entity = new HttpEntity<CustomerModel>(null,headers);
        CustomerModel customer = restTemplate.exchange(customerRequestUri, HttpMethod.GET, entity, CustomerModel.class, customerId)
                .getBody();
        return customer;
    }

    public String getAdmin(String header){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        HttpEntity<String> entity = new HttpEntity<>(null,headers);
        String adminId = restTemplate.exchange(adminRequestUri, HttpMethod.GET, entity, String.class).getBody();
        return adminId;
    }

}