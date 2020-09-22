package com.cognizant.supplierservice.Controller;

import java.util.ArrayList;

import com.cognizant.supplierservice.Exception.ProductNotExistsException;
import com.cognizant.supplierservice.Model.Product;
import com.cognizant.supplierservice.Service.SupplierManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Controller
@RestController
@RequestMapping("/supplier")
public class SupplierManagementController {

    @Autowired
    SupplierManagementService supplierManagementService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${com.cognizant.AuthenticationService.getSupplierUri}")
    private String supplierUri;

    @PostMapping("/addSupplier/{supplierId}")
    public String addSupplier(@PathVariable("supplierId") String supplierId) {//add all product with quanity = 0
        System.out.println("inside controller");
        return supplierManagementService.addSupplier(supplierId);
    }


    public String getSupplierId(String userId, String headers){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization", headers);
        HttpEntity<String> entity = new HttpEntity<String>(null,header);
        ArrayList<String> supplier = restTemplate.exchange(supplierUri, HttpMethod.GET, entity, new ParameterizedTypeReference<ArrayList<String>>(){}, userId)
                .getBody();
        String supplierId = supplier.get(0);
        return supplierId;
        // return "sup2";
    }


    @GetMapping("/getAllProducts/{userId}")
   public ArrayList<Product> getAllProducts(@PathVariable("userId") String userId, @RequestHeader("Authorization") String header) {
   // public ArrayList<Product> getAllProducts(@PathVariable("userId") String userId) {
        System.out.println("inside controller");
       String supplierId = getSupplierId(userId, header);
      return supplierManagementService.getAllProducts(supplierId);
    }

    @GetMapping("/getProduct/{userId}/{productId}")
    public Product getProduct(@PathVariable("userId") String userId, @PathVariable("productId") String productId, @RequestHeader("Authorization") String header) {
        String supplierId = getSupplierId(userId, header);
        return supplierManagementService.getProduct(supplierId, productId);
    }

    @PostMapping("/updateQuantity/increase/{userId}/{productId}/{quantity}")
    public String increaseProductQuantity(@PathVariable("userId") String userId,@PathVariable("productId") String productId, @PathVariable("quantity") int quantity, @RequestHeader("Authorization") String header) 
    throws ProductNotExistsException{
        String supplierId = getSupplierId(userId, header);
        return supplierManagementService.increaseProductQuantity(supplierId,productId, quantity);
    }

    @PostMapping("/updateQuantity/decrease/{userId}/{productId}/{quantity}")
    public String decreaseProductQuantity(@PathVariable("userId") String userId,@PathVariable("productId") String productId, @PathVariable("quantity") int quantity, @RequestHeader("Authorization") String header) 
    throws ProductNotExistsException{
        String supplierId = getSupplierId(userId, header);
        return supplierManagementService.decreaseProductQuantity(supplierId,productId, quantity);
    }

    @PostMapping("/addProduct/{productId}")
    //public String addProduct(@PathVariable("productId") String productId,  @RequestHeader("Authorization") String header) throws ProductNotExistsException {
        public String addProduct(@PathVariable("productId") String productId) throws ProductNotExistsException {
            System.out.println("controller inside new  ");
        return supplierManagementService.addProduct(productId);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    //public String deleteProduct(@PathVariable("productId") String productId, @RequestHeader("Authorization") String header) {
        public String deleteProduct(@PathVariable("productId") String productId) {
        return supplierManagementService.deleteProduct(productId);
    }
}