package br.com.personal.wishlist.application.service;

import br.com.personal.wishlist.application.port.JWTServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class JWTService implements JWTServicePort {
    private static final String ISSUER = "wishlist";

    @Value("${security.jwt.secret-key}")
    public String secretKey;

    @Value("${security.jwt.expiration-time}")
    public Long jwtExpiration;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    @Override
    public String extractUsername(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    @Override
    public Long expiration() {
        return jwtExpiration;
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = JWT.require(algorithm)
                .build()
                .verify(token)
                .getExpiresAt();
        return expiration.before(new Date());
    }

    @Override
    public String generateToken(String subject, Instant issuedAt, Instant expiresAt) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(Date.from(issuedAt))
                    .withExpiresAt(Date.from(expiresAt))
                    .withSubject(subject)
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.FAIL_TO_GENERATE_TOKEN));
        }
    }

    @Override
    public String token(UserDetails userDetails) {
        return buildToken(userDetails, jwtExpiration);
    }

    @Override
    public String validToken(String token) {
        return verifyToken(token);
    }

    public String buildToken(UserDetails userDetails, long expiration) {
        Instant now = Instant.now();
        Instant expirationInstant = now.plusMillis(expiration);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expirationInstant))
                .sign(algorithm);
    }

    private String verifyToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.INVALID_TOKEN_EXPIRED));
        }
    }
}