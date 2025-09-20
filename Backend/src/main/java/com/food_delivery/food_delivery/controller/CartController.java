package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.CartRequest;
import com.food_delivery.food_delivery.io.CartResponse;
import com.food_delivery.food_delivery.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private  final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartRequest request) {
        String foodId = request.getFoodId();
        if (foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FoodId not found");
        }

        return new ResponseEntity<>(cartService.addToCart(request), HttpStatus.OK);
    }

    @GetMapping
    public CartResponse getCart()
    {
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart()
    {
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request)
    {
        String foodId = request.getFoodId();
        if (foodId == null || foodId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "FoodId not found");
        }
        return cartService.removeFromCart(request);
    }
}
