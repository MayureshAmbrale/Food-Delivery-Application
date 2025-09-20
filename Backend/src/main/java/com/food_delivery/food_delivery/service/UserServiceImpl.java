package com.food_delivery.food_delivery.service;

import com.food_delivery.food_delivery.entity.UserEntity;
import com.food_delivery.food_delivery.io.UserRequest;
import com.food_delivery.food_delivery.io.UserResponse;
import com.food_delivery.food_delivery.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationFacade authenticationFacade;
    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity user = convertToEntity(request);
        user = userRepository.save(user);

        return convertToResponse(user) ;
    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail =  authenticationFacade.getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(loggedInUserEmail).orElseThrow(()-> new UsernameNotFoundException("User name not found"));
        return user.getId() ;
    }

    public UserEntity convertToEntity(UserRequest request)
    {
        return UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
    public UserResponse convertToResponse(UserEntity user)
    {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
