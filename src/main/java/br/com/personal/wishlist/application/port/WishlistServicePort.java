package br.com.personal.wishlist.application.port;

import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;

import java.util.List;

public interface WishlistServicePort {
    List<WishlistResponse> findAll();
    WishlistResponse findByProduct(String wishlistId, String userId , String productName);
    WishlistResponse addWishlist(WishlistRequest wishlistRequest);
    void removeProduct(String wishlistId, String userId, String productId);
}