package br.com.personal.wishlist.domain.mapper;

import br.com.personal.wishlist.application.dto.request.ProductRequest;
import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);

    Product toResponseEntity(ProductResponse productResponse);
}
