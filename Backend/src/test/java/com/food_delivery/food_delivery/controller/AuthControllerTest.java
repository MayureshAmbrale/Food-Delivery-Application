package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.AuthenticationRequest;
import com.food_delivery.food_delivery.io.AuthenticationResponse;
import com.food_delivery.food_delivery.service.AppUserDetailsService;
import com.food_delivery.food_delivery.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthControllerTest {

    @Mock
    AuthenticationManager authManager;
    @Mock
    AppUserDetailsService userDetailsService;
    @Mock
    JwtUtils jwtUtils;
    @InjectMocks
    AuthController authController;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        log.info("Setup complete for AuthControllerTest");
    }

    @Test
    void loginSuccess() throws Exception {
        log.info("Testing loginSuccess");
        AuthenticationRequest req = new AuthenticationRequest("test@example.com", "password");
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn("jwt-token");

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.token").value("jwt-token"));

        verify(authManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername("test@example.com");
        verify(jwtUtils).generateToken(userDetails);

        log.info("loginSuccess test PASSED for user: {}", req.getEmail());
    }

    @Test
    void loginFailure_InvalidCredentials() throws Exception {
        log.info("Testing loginFailure_InvalidCredentials");
        AuthenticationRequest req = new AuthenticationRequest("test@example.com", "wrongpass");
        doThrow(new BadCredentialsException("Invalid")).when(authManager).authenticate(any());

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isUnauthorized());

        log.info("loginFailure_InvalidCredentials test PASSED: unauthorized response returned for user {}", req.getEmail());
    }
}
