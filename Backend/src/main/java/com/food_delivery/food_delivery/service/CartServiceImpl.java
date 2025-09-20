package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.entity.CartEntity;
import com.food_delivery.food_delivery.io.CartRequest;
import com.food_delivery.food_delivery.io.CartResponse;
import com.food_delivery.food_delivery.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private  final UserService userService;
    private final CartRepository cartRepository;
    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Optional<CartEntity> cartOptional = cartRepository.findByUserId(loggedInUserId);
        CartEntity cart = cartOptional.orElseGet(()-> new CartEntity(loggedInUserId,new HashMap<>()));
        Map<String,Integer> cartItem = cart.getItems();
        cartItem.put(request.getFoodId(),cartItem.getOrDefault(request.getFoodId(),0)+1);
        cart.setItems(cartItem);
        cart = cartRepository.save(cart);

        return concertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElse(new CartEntity(loggedInUserId,new HashMap<>()));

        return concertToResponse(entity);
    }

    @Override
    public void clearCart() {
        String loggedInUserId = userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepository.findByUserId(loggedInUserId)
                .orElseThrow(()-> new UsernameNotFoundException("Not cart found"));
        Map<String,Integer> cartItems = entity.getItems();
        if(cartItems.containsKey(request.getFoodId()))
        {
            if(cartItems.get(request.getFoodId() ) >0)
            {
                int currentQty = cartItems.get(request.getFoodId());
                cartItems.put(request.getFoodId(),currentQty-1);
            }
            else {
                cartItems.remove(request.getFoodId());
            }
            entity = cartRepository.save(entity);
        }

        return concertToResponse(entity);

    }

    public CartResponse concertToResponse(CartEntity entity)
    {
        return CartResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .items(entity.getItems())
                .build();
    }
}
