package com.food_delivery.food_delivery.io;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();;
    private String orderStatus;
}
