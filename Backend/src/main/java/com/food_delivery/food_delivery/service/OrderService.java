package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.io.OrderRequest;
import com.food_delivery.food_delivery.io.OrderResponse;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest orderRequest) throws RazorpayException;

    void verifyPayment(Map<String,String> paymentData,String status);

    List<OrderResponse> getUserOrders();

    void  removeOrder(String orderId);

    List<OrderResponse> getOrdersOfAllUsers();

    void updateStatus(String orderId,String status);

}
