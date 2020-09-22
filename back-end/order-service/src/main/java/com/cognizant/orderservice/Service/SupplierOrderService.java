package com.cognizant.orderservice.Service;

import java.util.ArrayList;
import java.util.List;

import com.cognizant.orderservice.Dao.DbOperation;
import com.cognizant.orderservice.Model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierOrderService {

    @Autowired
    DbOperation dbOperation;

    @Autowired
    OrderDeliveryRequestService orderDeliveryRequestService;

   

    public List<Order> getOrdersForSupplier(String supplierId) {
        return dbOperation.getOrderOnSupplierId( supplierId);
    }

    public String allocateOrder(String orderId,String header){
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        String supplierId = obtainedOrder.getSupplierId();
        ArrayList<String> deliveryList = orderDeliveryRequestService.getDeliveryDetails(supplierId, header);
            Integer delivery=0;
            Integer baseQuantity = 25;
        while(delivery<deliveryList.size()){
            String deliveryId = deliveryList.get(delivery);
            log.info("id"+deliveryId);
            List<Order> obtainedOrderList = dbOperation.getOrderOnDeliveryId( deliveryId);
            if(obtainedOrderList.size()<=baseQuantity){
               obtainedOrder.setDeliveryId(deliveryId);
               obtainedOrder.setStatus("adminApproved");
               break;
            }
            delivery++;
            if(delivery==deliveryList.size()){
                baseQuantity+=25;
                delivery=0;
            }
        }
        dbOperation.update( obtainedOrder);
        return ("Allocation successful");
    }
}