package com.cognizant.orderservice.Service;

import com.cognizant.orderservice.Model.OrderProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProductRequestService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.ProductService.getProductByIdUri}")
    private String productByIdUri;

    @Value("${com.cognizant.ProductService.increaseQuantityUri}")
    private String increaseQuantityUri;

    @Value("${com.cognizant.ProductService.decreaseQuantityUri}")
    private String decreaseQuantityUri;

    public OrderProductModel getProductById(String supplierId, String productId, String header) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        log.info("header"+headers);
        HttpEntity<String> entity = new HttpEntity<String>(productId,headers);

        OrderProductModel productObject = restTemplate
                .exchange(productByIdUri, HttpMethod.GET, entity, OrderProductModel.class,supplierId, productId).getBody();
                System.out.println("product"+productObject);
        return productObject;
    }

    public String increaseProductQuantity(String supplierId, String productId, Integer quantity, String header){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        log.info("header"+headers);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String response = restTemplate
        .exchange(increaseQuantityUri, HttpMethod.POST, entity, String.class,supplierId, productId, quantity).getBody();
        return response;

    }

    
    public String decreaseProductQuantity(String supplierId, String productId, Integer quantity, String header){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",header);
        log.info("header"+headers);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String response = restTemplate
        .exchange(decreaseQuantityUri, HttpMethod.POST, entity, String.class,supplierId, productId, quantity).getBody();
        return response;

    }
}
