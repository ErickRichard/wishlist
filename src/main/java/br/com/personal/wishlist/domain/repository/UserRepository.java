package br.com.personal.wishlist.domain.repository;

import br.com.personal.wishlist.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String username);
}
