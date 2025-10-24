package com.food_delivery.food_delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_delivery.food_delivery.io.FoodRequest;
import com.food_delivery.food_delivery.io.FoodResponse;
import com.food_delivery.food_delivery.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
class FoodControllerTest {

    @Mock
    FoodService foodService;
    @InjectMocks
    FoodController foodController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
        log.info("FoodControllerTest setup complete");
    }

    @Test
    void addFoodSuccess() throws Exception {
        log.info("Testing addFoodSuccess: valid multipart request with correct JSON and file");
        FoodRequest req = new FoodRequest();
        req.setName("Pizza");
        req.setCategory("Italian");
        req.setPrice(10.5);
        req.setDescription("Tasty pizza");

        String jsonString = objectMapper.writeValueAsString(req);
        MockMultipartFile foodPart = new MockMultipartFile(
                "food", "food.json", "application/json", jsonString.getBytes()
        );
        MockMultipartFile filePart = new MockMultipartFile(
                "file", "testfile.txt", MediaType.TEXT_PLAIN_VALUE, "File content goes here".getBytes()
        );

        mockMvc.perform(multipart("/api/foods")
                        .file(foodPart)
                        .file(filePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(foodService).addFood(any(), any());
        log.info("addFoodSuccess passed with food name: {}", req.getName());
    }

    @Test
    void addFoodFailure_InvalidJson() throws Exception {
        log.info("Testing addFoodFailure_InvalidJson: invalid JSON for food part");
        MockMultipartFile filePart = new MockMultipartFile(
                "file", "testfile.txt", MediaType.TEXT_PLAIN_VALUE, "test data".getBytes()
        );
        // food as param will not work for multipart, so let's intentionally pass a bad part for coverage
        MockMultipartFile badFood = new MockMultipartFile(
                "food", "food.json", "application/json", "invalid json".getBytes()
        );

        mockMvc.perform(multipart("/api/foods")
                        .file(filePart)
                        .file(badFood)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
        log.info("addFoodFailure_InvalidJson passed (bad request as expected)");
    }

    @Test
    void readFoodsAll() throws Exception {
        log.info("Testing readFoodsAll: should return list of foods");
        when(foodService.readFood()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/foods"))
                .andExpect(status().isOk());
        verify(foodService).readFood();
        log.info("readFoodsAll passed");
    }

    @Test
    void readFoodById() throws Exception {
        log.info("Testing readFoodById: lookup by id");
        when(foodService.readFood("id1")).thenReturn(new FoodResponse());
        mockMvc.perform(get("/api/foods/id1"))
                .andExpect(status().isOk());
        verify(foodService).readFood("id1");
        log.info("readFoodById passed for id1");
    }

    @Test
    void deleteFood() throws Exception {
        log.info("Testing deleteFood: delete food by id");
        mockMvc.perform(delete("/api/foods/id1"))
                .andExpect(status().isNoContent());
        verify(foodService).deleteFood("id1");
        log.info("deleteFood passed for id1");
    }
}
