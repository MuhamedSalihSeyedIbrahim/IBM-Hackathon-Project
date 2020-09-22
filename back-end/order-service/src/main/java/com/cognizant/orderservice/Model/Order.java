package com.cognizant.orderservice.Model;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends RootModel {

    
    private String orderId;

    private String supplierId;

    private String customerId;

    private Float totalPrice;

    private String status;

    private ArrayList<OrderList> orderList;

    private String deliveryId;

    private String comments;

    private Integer rating;

    private String adminId;

    public Order(String _rev, String _id, String docType, String orderId, String supplierId, String customerId, Float totalPrice, String status,
            ArrayList<OrderList> orderList, String deliveryId, String comments, Integer rating, String adminId) {
                super(_rev, _id, docType);
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.customerId = customerId;
        this.deliveryId = deliveryId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderList = orderList;
        this.comments = comments;
        this.rating = rating;
        this.adminId = adminId;
    }

    
    
}