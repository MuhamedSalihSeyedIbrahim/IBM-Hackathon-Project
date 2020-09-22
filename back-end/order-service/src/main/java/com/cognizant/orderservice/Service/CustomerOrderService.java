package com.cognizant.orderservice.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cognizant.orderservice.Dao.DbOperation;
import com.cognizant.orderservice.Model.Coordinates;
import com.cognizant.orderservice.Model.CustomerModel;
import com.cognizant.orderservice.Model.Order;
import com.cognizant.orderservice.Model.OrderList;
import com.cognizant.orderservice.Model.OrderProductModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerOrderService {

    @Autowired
    DbOperation dbOperation;
    @Autowired
    OrderRequestService orderRequestService;
    @Autowired
    OrderProductRequestService orderProductRequestService;

    private Order order;


    public String addToCart(String customerId, String productId, Integer quantity, String headers) {
        log.info("in service");
        Order obtainedOrder = dbOperation.getSupplierPendingRequest(customerId);
        log.info("supplier" + obtainedOrder);
        int flag;
        String supplierId;

        if (obtainedOrder == null) {
            log.info("null");
            this.order = new Order();
            UUID uuid = UUID.randomUUID();
            this.order.setOrderId(uuid.toString());
            this.order.setCustomerId(customerId);
            supplierId = orderRequestService.getSupplier(customerId, headers);
            this.order.setSupplierId(supplierId);
            this.order.setOrderList(new ArrayList<OrderList>());
            this.order.setTotalPrice(0f);
            this.order.setAdminId(orderRequestService.getAdmin(headers));
            this.order.setDocType("order");
            flag=1;
        } else {
            log.info("else part");
            flag=0;
            log.info("order" + obtainedOrder);
            this.order = obtainedOrder;
            supplierId = obtainedOrder.getSupplierId();
            // this.order.setTotalPrice(0f);
        }
        OrderProductModel product = orderProductRequestService.getProductById(supplierId, productId, headers);
            ArrayList<OrderList> orderList = this.order.getOrderList();
            OrderList newOrder = new OrderList();
            newOrder.setProductId(productId);
            newOrder.setProductName(product.getProductName());
            Float productPrice = quantity * (product.getPrice());
            Float totalPrice = this.order.getTotalPrice();
            totalPrice += productPrice;
            newOrder.setProductQuantity(quantity);
            newOrder.setPrice(productPrice);
            log.info("new order" + newOrder);
            orderList.add(newOrder);

            this.order.setTotalPrice(totalPrice);
            this.order.setOrderList(orderList);
            this.order.setStatus("waiting");

            if(flag==1)
            dbOperation.save(this.order);
            if(flag==0)
            dbOperation.update(this.order);

            String response = orderProductRequestService.decreaseProductQuantity(supplierId, productId, quantity, headers);
        log.info("Quantity altered"+response);
        log.info("id" + this.order);
        return ("Order added to cart");
    }

    public List<Order> viewCart(String customerId) {
        return dbOperation.getOrderOnCustomerId(customerId);
    }

    public String updateCartProductQuantity(String productId, Integer quantity, String customerId, String headers) {
        Order obtainedOrder = dbOperation.getSupplierPendingRequest(customerId);
        String supplierId = obtainedOrder.getSupplierId();
        ArrayList<OrderList> obtainedOrderList = obtainedOrder.getOrderList();
        OrderProductModel obtainedProduct = orderProductRequestService.getProductById(supplierId, productId, headers);
        for (int product = 0; product < obtainedOrderList.size(); product++) {
            if (obtainedOrderList.get(product).getProductId().equals(productId)) {
                Integer obtainedQuantity = obtainedOrderList.get(product).getProductQuantity();
                log.info("quantity" + obtainedQuantity);
                Float obtainedPrice = obtainedQuantity * (obtainedProduct.getPrice());
                log.info("price" + obtainedPrice);
                Float totalPrice = obtainedOrder.getTotalPrice();
                log.info("total" + totalPrice);
                totalPrice -= obtainedPrice;
                log.info("tp" + totalPrice);
                obtainedOrderList.get(product).setProductQuantity(quantity);
                obtainedPrice = quantity * (obtainedProduct.getPrice());
                totalPrice += obtainedPrice;
                log.info("tp2" + totalPrice);
                obtainedOrderList.get(product).setPrice(obtainedPrice);
                obtainedOrder.setTotalPrice(totalPrice);
                Integer difference = (int) (quantity-obtainedQuantity);
                if(difference>=0){
                //decrease func
                orderProductRequestService.decreaseProductQuantity(supplierId, productId, quantity, headers);
                }
                else{
                difference=Math.abs(difference);
                //increase func
                orderProductRequestService.increaseProductQuantity(supplierId, productId, quantity, headers);
                }
                break;
            }
        }
        obtainedOrder.setOrderList(obtainedOrderList);
        dbOperation.update(obtainedOrder);
        return ("Product Quantity updated successfully");
    }

    public String deleteOrderProduct(String productId, String orderId, String headers) {
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        String supplierId = obtainedOrder.getSupplierId();
        OrderProductModel obtainedProduct = orderProductRequestService.getProductById(supplierId, productId, headers);
        ArrayList<OrderList> obtainedOrderList = obtainedOrder.getOrderList();
        for (int product = 0; product < obtainedOrderList.size(); product++) {
            if (obtainedOrderList.get(product).getProductId().equals(productId)) {
                Integer obtainedQuantity = obtainedOrderList.get(product).getProductQuantity();
                log.info("quantity" + obtainedQuantity);
                Float obtainedPrice = obtainedQuantity * (obtainedProduct.getPrice());
                log.info("price" + obtainedPrice);
                Float totalPrice = obtainedOrder.getTotalPrice();
                log.info("total" + totalPrice);
                totalPrice -= obtainedPrice;
                obtainedOrderList.remove(product);
                obtainedOrder.setTotalPrice(totalPrice);
                orderProductRequestService.increaseProductQuantity(supplierId, productId, obtainedQuantity, headers);
                break;
            }
        }
        obtainedOrder.setOrderList(obtainedOrderList);
        dbOperation.update( obtainedOrder);
        return ("Product deleted successfully");
    }

    public Coordinates getOrderToPlotInMap(String orderId, String header) {
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        log.info("order" + obtainedOrder);
        String customerId = obtainedOrder.getCustomerId();
        log.info("id" + customerId);
        return orderRequestService.getCustomerCoordinate(customerId, header);
    }

    public CustomerModel getCustomer(String orderId, String header) {
        Order obtainedOrder = dbOperation.getOrderOnOrderId( orderId);
        log.info("order" + obtainedOrder);
        String customerId = obtainedOrder.getCustomerId();
        log.info("id" + customerId);
        return orderRequestService.getCustomer(customerId, header);
    }

}