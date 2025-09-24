package com.security.jwt.service;

import com.security.jwt.entity.User;
import com.security.jwt.exception.InvalidCredentialsException;
import com.security.jwt.exception.UserNotFoundException;
import com.security.jwt.repository.UserRepository;
import com.security.jwt.service.jwt.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder; // Adicionar esta dependência


    public AuthenticationService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(Authentication authentication) {
        String password = (String) authentication.getCredentials();

        User user = userRepository.findByCpf(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        // Verifica se a senha está correta
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Senha incorreta");
        }
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