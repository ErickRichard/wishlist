package br.com.personal.wishlist.tests.service;

import br.com.personal.wishlist.application.service.UserService;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private String email;
    private String password;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        password = "password123";
    }

    @Test
    void shouldReturnUserDetailsWhenEmailAndPasswordAreValid() {
        var user = Mocks.createUser();
        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(user));
        UserDetails result = userService.findByEmailAndPassword(email, password);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(email, result.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Mockito.when(userRepository.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            userService.findByEmailAndPassword(email, password);
        });
        Assertions.assertEquals(MessageKey.USER_NOT_FOUND.getCode(), exception.getMessageError().getCode());
        Mockito.verify(userRepository).findByEmailAndPassword(email, password);
    }
}
