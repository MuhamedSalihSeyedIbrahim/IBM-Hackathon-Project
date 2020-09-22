package com.cognizant.orderservice.Service;

import java.util.ArrayList;


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
public class OrderDeliveryRequestService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.AuthenticationService.getDeliveryUri}")
    private String deliveryUri;

    public ArrayList<String> getDeliveryDetails(String supplierId, String header) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", header);
        log.info("header"+header);
        HttpEntity<String> entity = new HttpEntity<String>(null,headers);
        log.info("entity"+entity);
        ArrayList<String> delivery = restTemplate.exchange(deliveryUri, HttpMethod.GET, entity,new ParameterizedTypeReference<ArrayList<String>>(){}, supplierId).getBody();
                log.info("delivery"+delivery);
        return delivery;
    }

}