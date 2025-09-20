package com.food_delivery.food_delivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food_delivery.food_delivery.io.FoodRequest;
import com.food_delivery.food_delivery.io.FoodResponse;
import com.food_delivery.food_delivery.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@AllArgsConstructor
@CrossOrigin("*")
public class FoodController {

    private final FoodService service;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString,
                                @RequestPart("file")MultipartFile file)
    {
        ObjectMapper objectMapper = new ObjectMapper();

        FoodRequest request=null;

        try
        {
            request = objectMapper.readValue(foodString,FoodRequest.class);
        }catch (JsonProcessingException e)
        {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST,"Invalid json format");
        }
        return service.addFood(request,file);
    }

    @GetMapping
    public List<FoodResponse> readFoods()
    {
        return service.readFood();
    }
    @GetMapping("/{id}")
    public FoodResponse readFoods(@PathVariable String id)
    {
        return service.readFood(id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id)
    {
        service.deleteFood(id);
    }

}
