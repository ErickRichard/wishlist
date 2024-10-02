package br.com.personal.wishlist.tests.mock;

import br.com.personal.wishlist.application.dto.request.ProductRequest;
import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.application.dto.response.TokenResponse;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.User;
import br.com.personal.wishlist.domain.model.Wishlist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mocks {

    public static WishlistRequest createWishlistRequest() {
        ProductRequest product1 = ProductRequest.builder()
                .id("prod1")
                .name("Smartphone XYZ")
                .build();

        ProductRequest product2 = ProductRequest.builder()
                .id("prod2")
                .name("Fone de Ouvido Bluetooth")
                .build();

        ProductRequest product3 = ProductRequest.builder()
                .id("prod3")
                .name("Smartwatch ABC")
                .build();

        return WishlistRequest.builder()
                .userId("user123")
                .productRequestList(Arrays.asList(product1, product2, product3))
                .build();
    }

    public static WishlistRequest createWishlistRequestWithNewProduct() {
        return WishlistRequest.builder()
                .userId("1")
                .productRequestList(Collections.singletonList(ProductRequest.builder()
                        .id("new-product-id")
                        .name("New Product")
                        .build()))
                .build();
    }

    public static Wishlist createWishlist() {
        return Wishlist.builder()
                .id("1")
                .userId("1")
                .products(createListOfProducts())
                .build();
    }


    private static List<Product> createListOfProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = createProduct("prod1", "Smartphone XYZ", "Smartphone com tela de 6.5 polegadas, 128GB de armazenamento.", new BigDecimal("699.99"), 10);
        Product product2 = createProduct("prod2", "Fone de Ouvido Bluetooth", "Fones de ouvido sem fio com cancelamento de ruído.", new BigDecimal("249.90"), 15);
        Product product3 = createProduct("prod3", "Smartwatch ABC", "Relógio inteligente com monitoramento de atividades e notificações.", new BigDecimal("399.00"), 5);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        return products;

    }

    public static Product createProduct(String id, String name, String description, BigDecimal price, int quantity) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();
    }

    public static Product createDistinctProduct() {
        return Product.builder()
                .id("1")
                .name("NAME")
                .description("DESCRIPTION")
                .price(new BigDecimal("299.99"))
                .quantity(8)
                .build();
    }

    public static User createUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        return user;
    }

    public static ProductResponse createProductResponse() {
        return ProductResponse.builder()
                .id("product123")
                .name("Product Name")
                .description("Product Description")
                .price(new BigDecimal("10.00"))
                .build();
    }

    public static WishlistResponse createWishlistResponse() {
        return WishlistResponse.builder()
                .id("wishlist123")
                .productResponse(List.of(createProductResponse()))
                .build();
    }

    public static TokenRequest createTokenRequest() {
        return TokenRequest.builder()
                .email("user@example.com")
                .password("password")
                .build();
    }

    public static TokenResponse createTokenResponse(){
        return TokenResponse.builder()
                .token("validToken")
                .expire(3600000L)
                .build();
    }
}
