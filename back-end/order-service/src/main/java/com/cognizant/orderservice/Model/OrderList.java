package com.cognizant.orderservice.Model;

import lombok.Data;

@Data
public class OrderList {

    private String productId;

    private String productName;

    private Integer productQuantity;

    private Float price;

}