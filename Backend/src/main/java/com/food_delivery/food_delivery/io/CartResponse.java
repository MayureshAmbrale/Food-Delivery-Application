package com.food_delivery.food_delivery.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String id;
    private String userId;
    private Map<String,Integer> items = new HashMap<>();

}
