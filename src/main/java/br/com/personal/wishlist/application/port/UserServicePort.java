package br.com.personal.wishlist.application.port;


import org.springframework.security.core.userdetails.UserDetails;

public interface UserServicePort {
    UserDetails findByEmailAndPassword(String email, String password);
}
