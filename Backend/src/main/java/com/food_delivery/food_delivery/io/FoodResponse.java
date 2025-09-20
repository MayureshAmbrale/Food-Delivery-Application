package com.food_delivery.food_delivery.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponse {
    private String id;
    private String name;
    private  String description;
    private String imageUrl;
    private Double price;
    private String category;
}
