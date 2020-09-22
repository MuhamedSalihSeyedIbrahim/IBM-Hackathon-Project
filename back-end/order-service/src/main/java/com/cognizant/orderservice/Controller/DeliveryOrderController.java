package com.cognizant.orderservice.Controller;

import java.util.List;

import com.cognizant.orderservice.Model.Order;
import com.cognizant.orderservice.Service.DeliveryOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryOrder")
public class DeliveryOrderController {

    @Autowired
    DeliveryOrderService deliveryOrderService;

    @PutMapping("/start/{orderId}")
    public String startDelivery(@PathVariable("orderId") String orderId){
        return deliveryOrderService.startDelivery(orderId);
    }

    @PutMapping("/done/{orderId}")
    public String deliveryDone(@PathVariable("orderId") String orderId){
        return deliveryOrderService.deliveryDone(orderId);
    }

    @GetMapping("/{deliveryId}")
    public List<Order> getDeliveryOrder(@PathVariable("deliveryId") String deliveryId){
        return deliveryOrderService.getDeliveryOrder(deliveryId);
    }




}