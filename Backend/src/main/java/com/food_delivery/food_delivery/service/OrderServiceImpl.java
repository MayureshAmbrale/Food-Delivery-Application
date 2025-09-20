package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.entity.OrderEntity;
import com.food_delivery.food_delivery.io.OrderRequest;
import com.food_delivery.food_delivery.io.OrderResponse;
import com.food_delivery.food_delivery.repository.CartRepository;
import com.food_delivery.food_delivery.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Value("${razorpay_key}")
    private String RAZORPAY_KEY;
    @Value("${razorpay_secret}")
    private String RAZORPAY_SECRET;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;
    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {

        OrderEntity newOrder = convertToEntity(request);
        newOrder = orderRepository.save(newOrder);

        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY,RAZORPAY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) Math.round(newOrder.getAmount() * 100));
        orderRequest.put("currency","INR");
        orderRequest.put("payment_capture",1);

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        String loggedInUserId = userService.findByUserId();
        newOrder.setUserId(loggedInUserId);
        newOrder = orderRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
         String razorpayOrderId = paymentData.get("razorpay_order_id");
         OrderEntity existingOrder = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                 .orElseThrow(()-> new RuntimeException("Order not found"));
         existingOrder.setPaymentStatus(status);
         existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
         existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
         orderRepository.save(existingOrder);
         if("paid".equalsIgnoreCase(status))
         {
             cartRepository.deleteByUserId(existingOrder.getUserId());
         }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();

        List<OrderEntity> list = orderRepository.findByUserId(loggedInUserId);
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<OrderEntity> list = orderRepository.findAll();
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(String orderId, String status) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(
                ()-> new RuntimeException("Order not found")
        );
        order.setOrderStatus(status);
        orderRepository.save(order);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .userId(newOrder.getUserId())
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .paymentStatus(newOrder.getPaymentStatus())
                .orderStatus(newOrder.getOrderStatus())
                .email(newOrder.getEmail())
                .orderItems(newOrder.getOrderItems())
                .phoneNumber(newOrder.getPhoneNumber())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request)
    {
        return OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .orderItems(request.getOrderItems() != null ? request.getOrderItems() : new ArrayList<>())
                .amount(request.getAmount())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }
}
