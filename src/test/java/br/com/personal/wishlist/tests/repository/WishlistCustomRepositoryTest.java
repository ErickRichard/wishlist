package br.com.personal.wishlist.tests.repository;

import br.com.personal.wishlist.domain.model.Wishlist;
import br.com.personal.wishlist.domain.repository.WishlistCustomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishlistCustomRepositoryTest {

    @InjectMocks
    private WishlistCustomRepository wishlistCustomRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        wishlist = new Wishlist();
        wishlist.setId("wishlist123");
        wishlist.setUserId("user123");
    }

    static Stream<Arguments> wishlistFiltersProvider() {
        return Stream.of(
                Arguments.of("wishlist123", "user123", "product123", "Product Name", true),
                Arguments.of("wishlist123", null, null, null, true),
                Arguments.of(null, "user123", null, null, true),
                Arguments.of(null, null, "product123", null, true),
                Arguments.of(null, null, null, "Product Name", true),
                Arguments.of("empty", "user123", null, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("wishlistFiltersProvider")
    void shouldFindWishlistByVariousFilters(String wishlistId, String userId, String productId, String productName, boolean shouldExist) {
        when(mongoTemplate.findOne(any(), any())).thenReturn(shouldExist ? wishlist : null);
        Optional<Wishlist> result = wishlistCustomRepository.findWishlistByFilters(wishlistId, userId, productId, productName);
        if (shouldExist) {
            assertTrue(result.isPresent());
            Assertions.assertEquals("wishlist123", result.get().getId());
        } else {
            assertFalse(result.isPresent());
        }
    }
}
