package com.cognizant.orderservice.Controller;

import java.util.List;

import com.cognizant.orderservice.Model.Order;
import com.cognizant.orderservice.Service.OrderReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviewOrder")
public class ReviewOrderController {

    @Autowired
    OrderReviewService orderReviewService;

    @PutMapping("/reviewSubmit")
    public String reviewSubmit(@RequestBody Order reviewOrder){
        return orderReviewService.reviewSubmit(reviewOrder);
    }
    
    @GetMapping("/viewSupplier/{supplierId}")
    public List<Order> viewReviewsSupplier(@PathVariable("supplierId") String supplierId){
        return orderReviewService.viewReviewsSupplier(supplierId);
    }

    @GetMapping("/viewAdmin/{adminId}")
    public List<Order> viewReviewsAdmin(@PathVariable("adminId") String adminId){
        return orderReviewService.viewReviewsAdmin(adminId);
    }

    @PutMapping("/reviewedAdmin/{orderId}")
    public String reviewedAdmin(@PathVariable("orderId") String orderId){
        return orderReviewService.reviewedAdmin(orderId);
    }

    @PutMapping("/reviewedSupplier/{orderId}")
    public String reviewedSupplier(@PathVariable("orderId") String orderId){
        return orderReviewService.reviewedSupplier(orderId);
    }

    @GetMapping("/{orderId}")
    public Order reviewParticularOrder(@PathVariable("orderId") String orderId){
        return orderReviewService.reviewParticularOrder(orderId);
    }

    @GetMapping("/reviewDeliveredOrder/{customerId}")
    public List<Order> reviewOrders(@PathVariable("customerId") String customerId){
        return orderReviewService.reviewOrders(customerId);
    }

}