package com.food_delivery.food_delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "food")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodEntity {

    @Id
    private String id;
    private String name;
    private  String description;
    private String imageUrl;
    private Double price;
    private String category;
}
