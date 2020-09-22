package com.cognizant.orderservice.Controller;

import java.util.List;

import com.cognizant.orderservice.Model.Coordinates;
import com.cognizant.orderservice.Model.CustomerModel;
import com.cognizant.orderservice.Model.Order;
import com.cognizant.orderservice.Service.CustomerOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/customerOrder")
public class CustomerOrderController {

    @Autowired
    CustomerOrderService customerOrderService;

    @PostMapping("/addToCart/{customerId}/{productId}/{quantity}")
    public String addToCart(@PathVariable("customerId") String customerId, @PathVariable("productId") String productId,
            @PathVariable("quantity") Integer quantity, @RequestHeader("Authorization") String headers) {
        return customerOrderService.addToCart(customerId, productId, quantity, headers);
    }

    @GetMapping("/viewCart/{customerId}")
    public List<Order> viewCart(@PathVariable("customerId") String customerId) {
        return customerOrderService.viewCart(customerId);
    }

    @PutMapping("/updateCart/{productId}/{quantity}/{customerId}")
    public String updateCartProductQuantity(@PathVariable("customerId") String customerId,
            @PathVariable("productId") String productId, @PathVariable("quantity") Integer quantity, @RequestHeader("Authorization") String header) {
        return customerOrderService.updateCartProductQuantity(productId, quantity, customerId, header);
    }

    @DeleteMapping("/deleteCartProduct/{productId}/{orderId}")
    public String deleteOrderProduct(@PathVariable("productId") String productId,
            @PathVariable("orderId") String orderId, @RequestHeader("Authorization") String headers) {
        return customerOrderService.deleteOrderProduct(productId, orderId, headers);
    }

   @GetMapping("/customerCoordinates/{orderId}")
    public Coordinates getOrderToPlotInMap(@PathVariable("orderId") String orderId, @RequestHeader("Authorization") String header){
        return customerOrderService.getOrderToPlotInMap(orderId, header);
    } 
    
    @GetMapping("/getCustomer/{orderId}")
    public CustomerModel getCustomer(@PathVariable("orderId") String orderId, @RequestHeader("Authorization") String header){
        return customerOrderService.getCustomer(orderId, header);
    }
}