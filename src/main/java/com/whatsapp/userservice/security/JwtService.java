package com.whatsapp.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final Key signingKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts the user ID (subject) from a JWT token.
     *
     * @param token the JWT token string
     * @return the user UUID stored as the token subject
     * @throws JwtException if the token is invalid or expired
     */
    public UUID extractUserId(String token) {
        String subject = getClaims(token).getSubject();
        return UUID.fromString(subject);
    }

    /**
     * Validates a JWT token by checking signature and expiration.
     *
     * @param token the JWT token string
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            boolean expired = claims.getExpiration().before(new Date());
            if (expired) {
                log.warn("JWT token is expired");
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
