package com.cognizant.orderservice.Model;

import lombok.Data;

@Data
public class OrderProductModel {

    private String productId;

    private String productName;

    private Float price;

}