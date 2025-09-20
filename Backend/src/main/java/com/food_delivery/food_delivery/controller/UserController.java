package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.UserRequest;
import com.food_delivery.food_delivery.io.UserResponse;
import com.food_delivery.food_delivery.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request)
    {
        return new ResponseEntity<>(userService.registerUser(request),HttpStatus.CREATED);
    }
}
