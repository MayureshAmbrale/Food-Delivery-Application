package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.CartRequest;
import com.food_delivery.food_delivery.io.CartResponse;
import com.food_delivery.food_delivery.service.CartService;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CartControllerTest {

    @Mock
    CartService cartService;
    @InjectMocks
    CartController cartController;

    MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        log.info("CartControllerTest setup complete");
    }

    @Test
    void addToCartSuccess() throws Exception {
        log.info("Testing addToCartSuccess: valid foodId");
        CartRequest req = new CartRequest("food1");
        CartResponse resp = new CartResponse();
        when(cartService.addToCart(any())).thenReturn(resp);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk());

        verify(cartService).addToCart(any());
        log.info("addToCartSuccess passed for foodId: {}", req.getFoodId());
    }

    @Test
    void addToCartFailure_MissingFoodId() throws Exception {
        log.info("Testing addToCartFailure_MissingFoodId: missing foodId");
        CartRequest req = new CartRequest(null);

        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        log.info("addToCartFailure_MissingFoodId passed (bad request as expected)");
    }

    @Test
    void getCartSuccess() throws Exception {
        log.info("Testing getCartSuccess: should return cart response");
        CartResponse resp = new CartResponse();
        when(cartService.getCart()).thenReturn(resp);

        mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk());

        verify(cartService).getCart();
        log.info("getCartSuccess passed");
    }

    @Test
    void clearCartSuccess() throws Exception {
        log.info("Testing clearCartSuccess: should clear cart");
        mockMvc.perform(delete("/api/cart"))
                .andExpect(status().isNoContent());

        verify(cartService).clearCart();
        log.info("clearCartSuccess passed");
    }

    @Test
    void removeFromCartSuccess() throws Exception {
        log.info("Testing removeFromCartSuccess: valid foodId");
        CartRequest req = new CartRequest("food1");
        CartResponse resp = new CartResponse();
        when(cartService.removeFromCart(any())).thenReturn(resp);

        mockMvc.perform(post("/api/cart/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk());

        verify(cartService).removeFromCart(any());
        log.info("removeFromCartSuccess passed for foodId: {}", req.getFoodId());
    }

    @Test
    void removeFromCartFailure_MissingFoodId() throws Exception {
        log.info("Testing removeFromCartFailure_MissingFoodId: missing foodId");
        CartRequest req = new CartRequest("");

        mockMvc.perform(post("/api/cart/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isBadRequest());

        log.info("removeFromCartFailure_MissingFoodId passed (bad request as expected)");
    }
}
