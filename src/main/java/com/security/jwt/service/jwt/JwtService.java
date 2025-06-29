package com.security.jwt.service.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;

        var roles = authentication
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var claims = JwtClaimsSet.builder()
                .issuer("jwt")                      // Quem emitiu o token
                .issuedAt(now)                      // Quando foi emitido
                .expiresAt(now.plusSeconds(expiry)) // Quando expira
                .subject(authentication.getName())  // Usuário (subject)
                .claim("roles", roles)        // Roles customizadas
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    // Método para validar o token
    public boolean isTokenValid(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getExpiresAt().isAfter(Instant.now());
        } catch (Exception e) {
            return false;
        }
    }

    // Método para extrair informações do token
    public String extractUsername(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    // Método para extrair roles do token
    public List<String> extractRoles(String token) {
        try {
            Jwt jwt = decoder.decode(token);
            return jwt.getClaimAsStringList("roles");
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
