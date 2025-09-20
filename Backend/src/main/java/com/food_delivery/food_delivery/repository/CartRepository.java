package com.food_delivery.food_delivery.repository;

import com.food_delivery.food_delivery.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartEntity,String> {
    Optional<CartEntity> findByUserId(String userId);

    void deleteByUserId(String userId);
}
