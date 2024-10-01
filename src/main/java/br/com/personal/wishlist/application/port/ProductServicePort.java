package br.com.personal.wishlist.application.port;

import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.domain.model.Product;

public interface ProductServicePort {
    ProductResponse findProduct(String id);
    Product findByProduct(String id);
}
