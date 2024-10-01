package br.com.personal.wishlist.tests.service;

import br.com.personal.wishlist.application.service.JWTService;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    private static final String USERNAME = "user@example.com";
    private static final String ISSUER = "wishlist";
    private static final String TOKEN = "invalid.token";
    private static final long EXPIRATION = 3600000;

    @InjectMocks
    private JWTService jwtService;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Algorithm algorithm;

    @BeforeEach
    void setUp() {
        if (Objects.isNull(secretKey)) {
            ReflectionTestUtils.setField(jwtService, "secretKey", "mySecretKey");
            jwtService.init();
        }
        jwtService.jwtExpiration = EXPIRATION;
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        String token = jwtService.token(userDetails);
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token, JWT.decode(token).getToken());
    }

    @Test
    void shouldBuildTokenSuccessfully() {
        String token = jwtService.buildToken(userDetails, EXPIRATION);
        Assertions.assertNotNull(token);
        var jwt = JWT.decode(token);
        Assertions.assertEquals(ISSUER, jwt.getIssuer());
        Assertions.assertNotNull(jwt.getIssuedAt());
        Assertions.assertNotNull(jwt.getExpiresAt());
        Assertions.assertEquals(4, jwt.getClaims().size());
    }

    @Test
    void shouldReturnValidTokenSuccessfully() {
        Mockito.when(userDetails.getUsername()).thenReturn(USERNAME);
        String token = jwtService.generateToken(USERNAME, Instant.now(), Instant.now().plusMillis(3600000));
        Assertions.assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void shouldReturnSubjectWhenTokenIsValid() {
        String validToken = jwtService.buildToken(userDetails, EXPIRATION);
        Assertions.assertNull(jwtService.validToken(validToken));
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {
        String invalidToken = "invalid.token";
        Assertions.assertThrows(CoreRuleException.class, () -> {
            jwtService.validToken(invalidToken);
        });
    }

    @Test
    void shouldThrowCoreRuleExceptionForExpiredToken() {
        Mockito.when(JWT.require(algorithm)).thenThrow(new JWTVerificationException(MessageKey.INVALID_TOKEN_EXPIRED.getMessage()));
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            jwtService.validToken(TOKEN);
        });
        Assertions.assertEquals(MessageKey.INVALID_TOKEN_EXPIRED.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldReturnExpirationSuccessfully() {
        Long expiration = jwtService.expiration();
        Assertions.assertNotNull(expiration);
        Assertions.assertEquals(EXPIRATION, expiration);
    }
}