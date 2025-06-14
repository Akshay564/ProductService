package com.example.productservice.services;

import com.example.productservice.dtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TokenService {
    @Value("${users.api.host}")
    private String usersApiHost;

    RestTemplate restTemplate;

    public TokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validateToken(String token) {
        try {
            UserResponseDto userResponseDto = restTemplate.getForObject(usersApiHost + "validate/" + token, UserResponseDto.class);

            return userResponseDto != null && !userResponseDto.getUsername().isEmpty() &&  !userResponseDto.getEmail().isEmpty();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}
