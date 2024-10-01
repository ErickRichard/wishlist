package br.com.personal.wishlist.application.service;


import br.com.personal.wishlist.application.port.UserServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import br.com.personal.wishlist.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserServicePort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.USER_NOT_FOUND)));
    }
}
