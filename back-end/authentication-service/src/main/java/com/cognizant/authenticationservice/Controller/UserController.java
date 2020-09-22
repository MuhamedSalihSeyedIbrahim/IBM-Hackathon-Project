package com.cognizant.authenticationservice.Controller;

import java.util.ArrayList;
import java.util.List;

import com.cognizant.authenticationservice.Model.Coordinates;
import com.cognizant.authenticationservice.Model.User;
import com.cognizant.authenticationservice.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/supplier/{customerId}")
    public ArrayList<String> getSupplierId(@PathVariable String customerId) {
        return userService.getMappedSupplierId(customerId);
    }

    @GetMapping("/driver/{supplierId}")
    public ArrayList<String> getDriverId(@PathVariable String supplierId) {
        return userService.getMappedDriverId(supplierId);
    }

    @GetMapping("/coordinate/{customerId}")
    public Coordinates getCoordinates(@PathVariable String customerId) {
        return userService.getCoordinates(customerId);
    }

    @GetMapping("/suppliers/{pincode}")
    public List<User> getSuppliers(@PathVariable String pincode) {
        return userService.getSuppliers(pincode);
    }

    @GetMapping("/customer/{customerId}")
    public User getCustomer(@PathVariable String customerId) {
        return userService.getCustomer(customerId);
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return userService.getAdmin();
    }
}