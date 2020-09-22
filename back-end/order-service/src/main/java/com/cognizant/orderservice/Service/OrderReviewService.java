package com.cognizant.orderservice.Service;

import java.util.List;

import com.cognizant.orderservice.Dao.DbOperation;
import com.cognizant.orderservice.Model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderReviewService {

    @Autowired
    DbOperation dbOperation;

    public String reviewSubmit(Order reviewOrder){
        String orderId = reviewOrder.getOrderId();
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        obtainedOrder.setComments(reviewOrder.getComments());
        obtainedOrder.setRating(reviewOrder.getRating());
        dbOperation.update( obtainedOrder);
        return ("Review submitted successfully");

    }

    public List<Order> viewReviewsSupplier(String supplierId){
        return dbOperation.getReviewOrderOnSupplierId( supplierId);   
    }
    
    public List<Order> viewReviewsAdmin(String adminId){
        return dbOperation.getReviewOrderOnAdminId( adminId);
    }
    
    public String reviewedAdmin(String orderId){ 
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        if(obtainedOrder.getStatus().equals("delivered")){
            obtainedOrder.setStatus("adminViewedAndSupplierPending");
        }
        if(obtainedOrder.getStatus().equals("viewedBySupplier")){
            obtainedOrder.setStatus("viewed");
        }
        dbOperation.update( obtainedOrder);
        return ("Reviewed By Admin");
    }
    public String reviewedSupplier(String orderId){
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        if(obtainedOrder.getStatus().equals("delivered")){
            obtainedOrder.setStatus("viewedBySupplier");
        }
        if(obtainedOrder.getStatus().equals("adminViewedAndSupplierPending")){
            obtainedOrder.setStatus("viewed");
        }
        dbOperation.update( obtainedOrder);
        return ("Reviewed By Supplier");
    }

    public Order reviewParticularOrder(String orderId){ 
        return dbOperation.getOrderOnOrderId( orderId);
    }

    public List<Order> reviewOrders(String customerId){
        return dbOperation.getDeliveredOrderOnCustomerId( customerId);
    }
}