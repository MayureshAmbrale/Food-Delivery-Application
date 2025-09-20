package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.io.UserRequest;
import com.food_delivery.food_delivery.io.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest request);
    String findByUserId();
}
