package com.food_delivery.food_delivery.repository;

import com.food_delivery.food_delivery.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity,String> {

    Optional<UserEntity> findByEmail(String email);
}
