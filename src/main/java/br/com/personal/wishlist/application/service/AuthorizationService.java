package br.com.personal.wishlist.application.service;

import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.response.TokenResponse;
import br.com.personal.wishlist.application.port.AuthorizationServicePort;
import br.com.personal.wishlist.application.port.JWTServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import br.com.personal.wishlist.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements AuthorizationServicePort {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTServicePort jwtServicePort;

    @Autowired
    UserRepository userRepository;

    @Override
    public TokenResponse authenticateUser(TokenRequest tokenRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenRequest.getEmail(),
                        tokenRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(tokenRequest.getEmail()).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.USER_NOT_FOUND)));
        if (!passwordEncoder.matches(tokenRequest.getPassword(), user.getPassword())) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.INVALID_PASSWORD));
        }
        var token = jwtServicePort.token(user);
        return TokenResponse.builder()
                .token(token)
                .expire(jwtServicePort.expiration())
                .build();
    }
}