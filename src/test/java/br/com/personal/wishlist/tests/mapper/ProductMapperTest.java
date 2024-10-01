package br.com.personal.wishlist.tests.mapper;

import br.com.personal.wishlist.application.dto.request.ProductRequest;
import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.domain.mapper.ProductMapper;
import br.com.personal.wishlist.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testToEntity() {
        ProductRequest productRequest = ProductRequest.builder()
                .id("product1")
                .name("Product Name")
                .build();
        Product product = productMapper.toEntity(productRequest);
        assertEquals(productRequest.getId(), product.getId());
        assertEquals(productRequest.getName(), product.getName());
    }

    @Test
    void testToResponse() {
        Product product = Product.builder()
                .id("product1")
                .name("Product Name")
                .description("Product Description")
                .price(new BigDecimal("19.99"))
                .quantity(5)
                .build();

        ProductResponse productResponse = productMapper.toResponse(product);

        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getDescription(), productResponse.getDescription());
        assertEquals(product.getPrice(), productResponse.getPrice());
        assertEquals(product.getQuantity(), productResponse.getQuantity());
    }

    @Test
    void testToResponseEntity() {
        ProductResponse productResponse = new ProductResponse("product1", "Product Name", "Product Description", new BigDecimal("19.99"), 5);
        Product product = productMapper.toResponseEntity(productResponse);
        assertEquals(productResponse.getId(), product.getId());
        assertEquals(productResponse.getName(), product.getName());
        assertEquals(productResponse.getDescription(), product.getDescription());
        assertEquals(productResponse.getPrice(), product.getPrice());
        assertEquals(productResponse.getQuantity(), product.getQuantity());
    }
}