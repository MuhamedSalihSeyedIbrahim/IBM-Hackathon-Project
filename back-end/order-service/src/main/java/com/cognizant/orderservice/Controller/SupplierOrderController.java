package com.cognizant.orderservice.Controller;

import java.util.List;

import com.cognizant.orderservice.Model.Order;
import com.cognizant.orderservice.Service.SupplierOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/supplierOrder")
public class SupplierOrderController {

    @Autowired
    SupplierOrderService supplierOrderService;

    @GetMapping("/getSupplierOrder/{supplierId}")
    public List<Order> getOrdersForSupplier(@PathVariable("supplierId") String supplierId) {
        return supplierOrderService.getOrdersForSupplier(supplierId);
    }

    @PutMapping("/allocateOrder/{orderId}")
    public String allocateOrder(@PathVariable("orderId") String orderId, @RequestHeader("Authorization") String header) {        
        return supplierOrderService.allocateOrder(orderId, header);
    }
}