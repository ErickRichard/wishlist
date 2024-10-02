package br.com.personal.wishlist.tests.mapper;

import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.domain.mapper.WishlistMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.Wishlist;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WishlistMapperTest {
    private final WishlistMapper wishlistMapper = Mappers.getMapper(WishlistMapper.class);

    private Product product;
    private List<Product> products;
    private Wishlist wishlist;
    private WishlistRequest wishlistRequest;

    @BeforeEach
    void setUp() {
        product = Mocks.createDistinctProduct();
        products = List.of(Mocks.createDistinctProduct());
        wishlist = Mocks.createWishlist();
        wishlistRequest = Mocks.createWishlistRequest();
    }

    @Test
    void testToEntity() {
        Wishlist wishlist = wishlistMapper.toEntity(wishlistRequest, products);
        assertEquals(wishlistRequest.getUserId(), wishlist.getUserId());
        assertEquals(products, wishlist.getProducts());
    }

    @Test
    void testToResponse() {
        var wishlist = Wishlist.builder()
                .id("wishlist1")
                .userId("user1")
                .products(List.of(Mocks.createProduct("product1", "Product Name", "Product Description", new BigDecimal("19.99"), 5)))
                .build();
        WishlistResponse wishlistResponse = wishlistMapper.toResponse(wishlist);
        assertEquals(wishlist.getId(), wishlistResponse.getId());
        assertEquals(1, wishlistResponse.getProductResponse().size());
        assertEquals("product1", wishlistResponse.getProductResponse().get(0).getId());
    }

    @Test
    void testUpdateEntity() {
        wishlistMapper.updateEntity(Wishlist.builder()
                .id("wishlist1")
                .userId("user1")
                .products(List.of(product))
                .build(), List.of(Mocks.createProduct("product2", "New Product", "New Product Description", new BigDecimal("29.99"), 3)));
        assertEquals(3, wishlist.getProducts().size());
        assertEquals("prod2", wishlist.getProducts().get(1).getId());
        assertEquals("prod1", wishlist.getProducts().get(0).getId());
    }
}
