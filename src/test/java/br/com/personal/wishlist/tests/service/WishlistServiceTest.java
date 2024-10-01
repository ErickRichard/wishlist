package br.com.personal.wishlist.tests.service;

import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.application.service.ProductService;
import br.com.personal.wishlist.application.service.WishlistService;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.mapper.WishlistMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.Wishlist;
import br.com.personal.wishlist.domain.repository.WishlistCustomRepository;
import br.com.personal.wishlist.domain.repository.WishlistRepository;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    private static final String WISHLIST_ID = "1";
    private static final String USER_ID = "1";
    private static final String PRODUCT_ID = "1";
    private static final String PRODUCT_NAME = "Smartphone XYZ";

    @InjectMocks
    private WishlistService wishlistService;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private WishlistCustomRepository wishlistCustomRepository;

    @Mock
    private ProductService productService;

    @Mock
    private WishlistMapper wishlistMapper;

    private Wishlist wishlist;
    private WishlistRequest wishlistRequest;

    @BeforeEach
    void setUp() {
        wishlist = Mocks.createWishlist();
        wishlistRequest = Mocks.createWishlistRequest();
    }

    @Test
    void shouldReturnWishlistsWhenDataIsPresent() {
        Mockito.when(wishlistRepository.findAll()).thenReturn(List.of(wishlist));
        List<WishlistResponse> response = wishlistService.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Mockito.verify(wishlistRepository).findAll();
    }

    @Test
    void shouldReturnWishlistWhenProductFound() {
        Mockito.when(wishlistCustomRepository.findWishlistByFilters(Mockito.anyString(), Mockito.anyString(), Mockito.isNull(), Mockito.anyString())).thenReturn(Optional.of(wishlist));
        Mockito.when(wishlistMapper.toResponse(wishlist)).thenReturn(new WishlistResponse());
        WishlistResponse response = wishlistService.findByProduct(WISHLIST_ID, USER_ID, PRODUCT_NAME);
        Assertions.assertNotNull(response);
        Mockito.verify(wishlistCustomRepository).findWishlistByFilters(WISHLIST_ID, USER_ID, null, PRODUCT_NAME);
    }

    @Test
    void shouldAddWishlistWhenNotExists() {
        Mockito.when(wishlistRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(wishlistMapper.toEntity(Mockito.any(), Mockito.any())).thenReturn(new Wishlist());
        Mockito.when(wishlistMapper.toResponse(Mockito.any())).thenReturn(new WishlistResponse());
        WishlistResponse response = wishlistService.addWishlist(wishlistRequest);
        Assertions.assertNotNull(response);
        Mockito.verify(wishlistRepository).save(Mockito.any());
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        Mockito.when(wishlistCustomRepository.findWishlistByFilters(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.isNull())).thenReturn(Optional.of(wishlist));
        wishlistService.removeProduct(WISHLIST_ID, USER_ID, PRODUCT_ID);
        Assertions.assertFalse(wishlist.getProducts().isEmpty());
        Mockito.verify(wishlistRepository).save(wishlist);
    }

    @Test
    void shouldThrowExceptionWhenWishlistNotFound() {
        Mockito.when(wishlistCustomRepository.findWishlistByFilters(Mockito.anyString(), Mockito.anyString(), Mockito.isNull(), Mockito.anyString())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            wishlistService.findByProduct(WISHLIST_ID, USER_ID, PRODUCT_NAME);
        });
        Assertions.assertEquals(MessageKey.WISHLIST_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenNoWishlistsFound() {
        Mockito.when(wishlistRepository.findAll()).thenReturn(new ArrayList<>());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            wishlistService.findAll();
        });
        Assertions.assertEquals(MessageKey.PRODUCT_WISHLIST_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Mockito.when(wishlistCustomRepository.findWishlistByFilters(Mockito.eq(WISHLIST_ID), Mockito.eq(USER_ID), Mockito.isNull(), Mockito.anyString())).thenReturn(Optional.of(wishlist));
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            wishlistService.findByProduct(WISHLIST_ID, USER_ID, "INVALID PRODUCT");
        });
        Assertions.assertEquals(MessageKey.PRODUCT_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyRegistered() {
        Mockito.when(wishlistRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(wishlist));
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            wishlistService.addWishlist(wishlistRequest);
        });
        Assertions.assertEquals(MessageKey.PRODUCT_ALREADY_REGISTERED.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenWishlistExceedsLimit() {
        wishlist.setProducts(Collections.nCopies(20, Mocks.createDistinctProduct()));
        Mockito.when(wishlistRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(wishlist));
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> wishlistService.addWishlist(wishlistRequest));
        Assertions.assertEquals(MessageKey.LIMIT_EXCEEDED.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldUpdateWishlistWhenProductIsNew() {
        var wishlistRequest = Mocks.createWishlistRequestWithNewProduct();
        List<Product> newProducts = List.of(Mocks.createDistinctProduct());
        Mockito.when(wishlistRepository.findByUserId(Mockito.anyString())).thenReturn(Optional.of(wishlist));
        Mockito.when(productService.findByProduct(Mockito.anyString())).thenReturn(newProducts.get(0));
        wishlistService.addWishlist(wishlistRequest);
        Mockito.verify(wishlistMapper, Mockito.times(1)).updateEntity(Mockito.eq(wishlist), Mockito.eq(newProducts));
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentProduct() {
        Mockito.when(wishlistCustomRepository.findWishlistByFilters(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.isNull())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            wishlistService.removeProduct(WISHLIST_ID, USER_ID, PRODUCT_ID);
        });
        Assertions.assertEquals(MessageKey.WISHLIST_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }
}