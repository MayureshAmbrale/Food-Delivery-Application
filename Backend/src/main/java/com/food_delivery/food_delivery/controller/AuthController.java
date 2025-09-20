package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.AuthenticationRequest;
import com.food_delivery.food_delivery.io.AuthenticationResponse;
import com.food_delivery.food_delivery.service.AppUserDetailsService;
import com.food_delivery.food_delivery.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private  final AppUserDetailsService appUserDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtils.generateToken(userDetails);
        return new AuthenticationResponse(request.getEmail(), jwtToken);
    }
}
