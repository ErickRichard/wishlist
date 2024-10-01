package br.com.personal.wishlist.application.port;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JWTServicePort {
    String generateToken(String subject, Instant issuedAt, Instant expiresAt);
    String token(UserDetails userDetails);
    String validToken(String token);
    Boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);
    Long expiration();
}
