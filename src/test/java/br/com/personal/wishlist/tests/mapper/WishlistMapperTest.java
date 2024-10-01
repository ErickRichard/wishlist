package br.com.personal.wishlist.tests.mapper;

import br.com.personal.wishlist.application.dto.request.ProductRequest;
import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.domain.mapper.WishlistMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.Wishlist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WishlistMapperTest {
    private final WishlistMapper wishlistMapper = Mappers.getMapper(WishlistMapper.class);

    @Test
    void testToEntity() {
        WishlistRequest wishlistRequest = WishlistRequest.builder()
                .userId("user1")
                .productRequestList(List.of(
                        ProductRequest.builder()
                                .id("product1")
                                .name("Product Name")
                                .build()))
                .build();
        Product product = new Product("product1", "Product Name", "Product Description", new BigDecimal("19.99"), 5);
        List<Product> products = Collections.singletonList(product);
        Wishlist wishlist = wishlistMapper.toEntity(wishlistRequest, products);
        assertEquals(wishlistRequest.getUserId(), wishlist.getUserId());
        assertEquals(products, wishlist.getProducts());
    }

    @Test
    void testToResponse() {
        Product product = new Product("product1", "Product Name", "Product Description", new BigDecimal("19.99"), 5);
        Wishlist wishlist = Wishlist.builder()
                .id("wishlist1")
                .userId("user1")
                .products(Collections.singletonList(product))
                .build();
        WishlistResponse wishlistResponse = wishlistMapper.toResponse(wishlist);
        assertEquals(wishlist.getId(), wishlistResponse.getId());
        assertEquals(1, wishlistResponse.getProductResponse().size());
        assertEquals("product1", wishlistResponse.getProductResponse().get(0).getId());
    }

    @Test
    void testUpdateEntity() {
        Product existingProduct = new Product("product1", "Product Name", "Product Description", new BigDecimal("19.99"), 5);
        Wishlist wishlist = Wishlist.builder()
                .id("wishlist1")
                .userId("user1")
                .products(Collections.singletonList(existingProduct))
                .build();
        Product newProduct = new Product("product2", "New Product", "New Product Description", new BigDecimal("29.99"), 3);
        List<Product> newProducts = Collections.singletonList(newProduct);
        wishlistMapper.updateEntity(wishlist, newProducts);
        assertEquals(2, wishlist.getProducts().size());
        assertEquals("product1", wishlist.getProducts().get(1).getId());
        assertEquals("product2", wishlist.getProducts().get(0).getId());
    }
}
