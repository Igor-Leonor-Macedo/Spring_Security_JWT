package com.security.jwt.service;

import com.security.jwt.entity.User;
import com.security.jwt.exception.UserNotFoundException;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.service.jwt.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private JwtService jwtService;
    private UserRepository userRepository;

    public AuthenticationService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String authenticate(Authentication authentication) {
        User user = userRepository.findByCpf(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        return jwtService.generateToken(authentication, user);
    }

    public boolean validateToken(String token) {
        return jwtService.isTokenValid(token);
    }

    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    public List<String> extractRoles(String token) {
        return jwtService.extractRoles(token);
    }
}