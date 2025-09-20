package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.io.CartRequest;
import com.food_delivery.food_delivery.io.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest request);
}
