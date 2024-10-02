package br.com.personal.wishlist.tests.mapper;

import br.com.personal.wishlist.application.dto.request.ProductRequest;
import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.domain.mapper.ProductMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = Mocks.createDistinctProduct();
        productRequest = Mocks.createProductRequest();
        productResponse = Mocks.createProductResponse();
    }

    @Test
    void testToEntity() {
        var product = productMapper.toEntity(productRequest);
        assertEquals(productRequest.getId(), product.getId());
        assertEquals(productRequest.getName(), product.getName());
    }

    @Test
    void testToResponse() {
        var productResponse = productMapper.toResponse(product);
        assertEquals(product.getId(), productResponse.getId());
        assertEquals(product.getName(), productResponse.getName());
        assertEquals(product.getDescription(), productResponse.getDescription());
        assertEquals(product.getPrice(), productResponse.getPrice());
        assertEquals(product.getQuantity(), productResponse.getQuantity());
    }

    @Test
    void testToResponseEntity() {
        var product = productMapper.toResponseEntity(productResponse);
        assertEquals(productResponse.getId(), product.getId());
        assertEquals(productResponse.getName(), product.getName());
        assertEquals(productResponse.getDescription(), product.getDescription());
        assertEquals(productResponse.getPrice(), product.getPrice());
        assertEquals(productResponse.getQuantity(), product.getQuantity());
    }
}