package br.com.personal.wishlist.domain.mapper;


import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    Wishlist toEntity(WishlistRequest wishlistRequest, List<Product> products);

    @Mapping(target = "productResponse", source = "products")
    WishlistResponse toResponse(Wishlist wishlist);

    default void updateEntity(@MappingTarget Wishlist wishlist, List<Product> newProducts) {
        List<Product> updatedProducts = newProducts.stream()
                .filter(newProduct ->
                        wishlist.getProducts().stream().noneMatch(existingProduct ->
                                existingProduct.getId().equals(newProduct.getId())
                        )
                )
                .collect(Collectors.toList());

        updatedProducts.addAll(wishlist.getProducts());
        wishlist.setProducts(updatedProducts);
    }
}
