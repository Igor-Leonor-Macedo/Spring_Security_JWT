package com.security.jwt.controller;

import com.security.jwt.dto.LoginRequestDto;
import com.security.jwt.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST}) // Aplica CORS para todos os métodos desse controlador
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("authenticateUser")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

    @PostMapping("authenticate")
    public String authenticate(@RequestBody LoginRequestDto loginRequestDTO) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getCPF(), loginRequestDTO.getPassword());
        return authenticationService.authenticate(authentication);
    }
    @PostMapping("validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("valid", false, "message", "Token não fornecido"));
        }

        boolean isValid = authenticationService.validateToken(token);
        Map<String, Object> response = new HashMap<>();

        if (isValid) {
            String username = authenticationService.extractUsername(token);
            List<String> roles = authenticationService.extractRoles(token);

            response.put("valid", true);
            response.put("username", username);
            response.put("roles", roles);
        } else {
            response.put("valid", false);
            response.put("message", "Token inválido ou expirado");
        }

        return ResponseEntity.ok(response);
    }
}

