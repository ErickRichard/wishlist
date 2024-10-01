package br.com.personal.wishlist.domain.repository;

import br.com.personal.wishlist.domain.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
