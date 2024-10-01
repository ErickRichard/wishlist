package br.com.personal.wishlist.tests.service;

import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.response.TokenResponse;
import br.com.personal.wishlist.application.port.JWTServicePort;
import br.com.personal.wishlist.application.service.AuthorizationService;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.model.User;
import br.com.personal.wishlist.domain.repository.UserRepository;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {
    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTServicePort jwtServicePort;

    @Mock
    private UserRepository userRepository;

    private User user;
    private TokenRequest tokenRequest;

    @BeforeEach
    void setUp() {
        tokenRequest = Mocks.createTokenRequest();
        user = Mocks.createUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        String email = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        Mockito.when(jwtServicePort.token(Mockito.any())).thenReturn("mockedToken");
        Mockito.when(jwtServicePort.expiration()).thenReturn(3600L);
        TokenResponse tokenResponse = authorizationService.authenticateUser(tokenRequest);
        Assertions.assertNotNull(tokenResponse);
        Assertions.assertEquals("mockedToken", tokenResponse.getToken());
        Assertions.assertEquals(3600L, tokenResponse.getExpire());
        Mockito.verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(email, password));
        Mockito.verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorizationService.authenticateUser(tokenRequest);
        });
        Assertions.assertEquals(MessageKey.USER_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenInvalidPassword() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", user.getPassword())).thenReturn(false);
        CoreRuleException exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorizationService.authenticateUser(tokenRequest);
        });

        Assertions.assertEquals(MessageKey.INVALID_PASSWORD.getCode(), exception.getMessageError().getCode());
    }
}
