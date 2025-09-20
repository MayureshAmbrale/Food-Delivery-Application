package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.OrderRequest;
import com.food_delivery.food_delivery.io.OrderResponse;
import com.food_delivery.food_delivery.service.OrderService;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws RazorpayException {
        return orderService.createOrderWithPayment(request);
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String,String> paymentData)
    {
        orderService.verifyPayment(paymentData,"paid");
    }
    @GetMapping
    public List<OrderResponse> getOrders()
    {
        return orderService.getUserOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable String orderId)
    {
        orderService.removeOrder(orderId);
    }

    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers()
    {
        return orderService.getOrdersOfAllUsers();
    }
    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId,@RequestParam String status)
    {
        orderService.updateStatus(orderId,status);
    }
}
