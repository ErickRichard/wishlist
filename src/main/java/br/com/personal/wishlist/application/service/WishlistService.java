package br.com.personal.wishlist.application.service;

import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.application.port.WishlistServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import br.com.personal.wishlist.domain.mapper.ProductMapper;
import br.com.personal.wishlist.domain.mapper.WishlistMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.Wishlist;
import br.com.personal.wishlist.domain.repository.WishlistCustomRepository;
import br.com.personal.wishlist.domain.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WishlistService implements WishlistServicePort {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    WishlistCustomRepository wishlistCustomRepository;

    @Autowired
    WishlistMapper wishlistMapper;

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<WishlistResponse> findAll() {
        var wishlists = wishlistRepository.findAll();
        if (wishlists.isEmpty()) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.PRODUCT_WISHLIST_NOT_FOUND));
        }
        return wishlists.stream().map(wishlist -> wishlistMapper.toResponse(wishlist)).toList();
    }

    @Override
    public WishlistResponse findByProduct(String wishlistId, String userId, String productName) {
        var wishlist = getWishlist(wishlistId, userId, null, productName);
        var product = wishlist.getProducts().stream().filter(item -> Objects.equals(item.getName(), productName)).findFirst();
        if (product.isEmpty()) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.PRODUCT_NOT_FOUND));
        }
        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public WishlistResponse addWishlist(WishlistRequest wishlistRequest) {
        Wishlist wishlist = wishlistRepository.findByUserId(wishlistRequest.getUserId()).orElse(null);
        List<Product> products = createProducts(wishlistRequest);
        if (Objects.nonNull(wishlist)) {
            validateLimitByClient(wishlist);
            validateProduct(wishlistRequest, wishlist);
            wishlistMapper.updateEntity(wishlist, products);
        } else {
            wishlist = wishlistMapper.toEntity(wishlistRequest, products);
        }
        wishlistRepository.save(wishlist);
        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public void removeProduct(String wishlistId, String userId, String productId) {
        var wishlist = getWishlist(wishlistId, userId, productId, null);
        var products = wishlist.getProducts();
        products.removeIf(product -> Objects.equals(product.getId(), productId));
        wishlistRepository.save(wishlist);
    }

    private List<Product> createProducts(WishlistRequest wishlistRequest) {
        List<Product> products = new ArrayList<>();
        wishlistRequest.getProductRequestList().forEach(productRequest -> {
            var product = productService.findByProduct(productRequest.getId());
            if (Objects.nonNull(product)) {
                products.add(product);
            }
        });
        return products;
    }

    private void validateLimitByClient(Wishlist wishlist) {
        if (wishlist.getProducts().size() >= 20) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.LIMIT_EXCEEDED));
        }
    }

    private void validateProduct(WishlistRequest wishlistRequest, Wishlist wishlist) {
        Set<String> productFind = wishlist.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        wishlistRequest.getProductRequestList().forEach(productRequest -> {
            if (productFind.contains(productRequest.getId())) {
                throw new CoreRuleException(MessageResponse.error(MessageKey.PRODUCT_ALREADY_REGISTERED));
            }
        });
    }

    private Wishlist getWishlist(String wishlistId, String userId, String productId, String productName) {
        return wishlistCustomRepository.findWishlistByFilters(wishlistId, userId, productId, productName).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.WISHLIST_NOT_FOUND)));
    }
}