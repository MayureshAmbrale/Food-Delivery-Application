package com.food_delivery.food_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_delivery.food_delivery.io.OrderRequest;
import com.food_delivery.food_delivery.io.OrderResponse;
import com.food_delivery.food_delivery.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class OrderControllerTest {

    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        log.info("OrderControllerTest setup complete");
    }

    @Test
    void createOrderWithPaymentSuccess() throws Exception {
        log.info("Testing createOrderWithPaymentSuccess: Should create an order and return 201");
        OrderRequest req = new OrderRequest();
        OrderResponse resp = new OrderResponse();
        when(orderService.createOrderWithPayment(any())).thenReturn(resp);

        mockMvc.perform(post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(orderService).createOrderWithPayment(any());
        log.info("createOrderWithPaymentSuccess passed");
    }

    @Test
    void verifyPaymentSuccess() throws Exception {
        log.info("Testing verifyPaymentSuccess: Should verify payment and return 200");
        Map<String, String> paymentData = Map.of("key", "value");

        mockMvc.perform(post("/api/orders/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentData)))
                .andExpect(status().isOk());

        verify(orderService).verifyPayment(any(), eq("paid"));
        log.info("verifyPaymentSuccess passed");
    }

    @Test
    void getOrdersSuccess() throws Exception {
        log.info("Testing getOrdersSuccess: Should return current user's orders");
        when(orderService.getUserOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());

        verify(orderService).getUserOrders();
        log.info("getOrdersSuccess passed");
    }

    @Test
    void removeOrderSuccess() throws Exception {
        log.info("Testing removeOrderSuccess: Should remove order by id and return 204");
        mockMvc.perform(delete("/api/orders/oid1"))
                .andExpect(status().isNoContent());

        verify(orderService).removeOrder("oid1");
        log.info("removeOrderSuccess passed for oid1");
    }

    @Test
    void getOrdersOfAllUsers() throws Exception {
        log.info("Testing getOrdersOfAllUsers: Should return all users' orders");
        when(orderService.getOrdersOfAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/orders/all"))
                .andExpect(status().isOk());

        verify(orderService).getOrdersOfAllUsers();
        log.info("getOrdersOfAllUsers passed");
    }

    @Test
    void updateOrderStatus() throws Exception {
        log.info("Testing updateOrderStatus: Should update order status and return 200");
        mockMvc.perform(patch("/api/orders/status/oid1")
                        .param("status", "shipped"))
                .andExpect(status().isOk());

        verify(orderService).updateStatus("oid1", "shipped");
        log.info("updateOrderStatus passed for oid1 with status 'shipped'");
    }
}
