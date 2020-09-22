package com.cognizant.orderservice.Service;

import java.util.List;

import com.cognizant.orderservice.Dao.DbOperation;
import com.cognizant.orderservice.Model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryOrderService {

    @Autowired
    DbOperation dbOperation;

    public String startDelivery(String orderId){
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        obtainedOrder.setStatus("outForDelivery");
        dbOperation.update( obtainedOrder);
        return ("Out For Delivery");
    }

    public String deliveryDone(String orderId){ 
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        obtainedOrder.setStatus("delivered");
        dbOperation.update( obtainedOrder);
        return ("Delivered successfully");
    }

    public List<Order> getDeliveryOrder(String deliveryId){
       return dbOperation.getOrderOnDeliveryId( deliveryId);

    }

}