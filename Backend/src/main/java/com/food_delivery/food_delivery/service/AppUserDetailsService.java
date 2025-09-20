package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.entity.UserEntity;
import com.food_delivery.food_delivery.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private  final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = repository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User name not found")
        );
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());

    }
}
