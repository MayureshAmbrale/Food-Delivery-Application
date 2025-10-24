package com.food_delivery.food_delivery.controller;

import com.food_delivery.food_delivery.io.UserRequest;
import com.food_delivery.food_delivery.io.UserResponse;
import com.food_delivery.food_delivery.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserControllerTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        log.info("UserControllerTest setup complete");
    }

    @Test
    void registerSuccess() throws Exception {
        log.info("Testing registerSuccess: Should register user and return 201");
        UserRequest req = new UserRequest();
        UserResponse resp = new UserResponse();

        when(userService.registerUser(any())).thenReturn(resp);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(userService).registerUser(any());
        log.info("registerSuccess passed for user registration");
    }
}
