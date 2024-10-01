package br.com.personal.wishlist.tests.service;

import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.application.service.ProductService;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.mapper.ProductMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.repository.ProductRepository;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private Product product;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = Mocks.createProduct("product123", "Product Name", "Product Description", new BigDecimal("10.00"), 10);
        productResponse = Mocks.createProductResponse();
    }

    @ParameterizedTest
    @MethodSource("productDataProvider")
    void shouldReturnProductResponseOrThrowException(String productId, boolean shouldExist) {
        if (shouldExist) {
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            when(productMapper.toResponse(product)).thenReturn(productResponse);
            ProductResponse response = productService.findProduct(productId);
            assertNotNull(response);
            assertEquals(product.getId(), response.getId());
            assertEquals(product.getName(), response.getName());
            assertEquals(product.getDescription(), response.getDescription());
            assertEquals(product.getPrice(), response.getPrice());
        } else {
            when(productRepository.findById(productId)).thenReturn(Optional.empty());
            CoreRuleException exception = assertThrows(CoreRuleException.class, () -> {
                productService.findProduct(productId);
            });
            assertEquals(MessageKey.PRODUCT_NOT_FOUND.getCode(), exception.getMessageError().getCode());
        }
    }

    @ParameterizedTest
    @MethodSource("productDataProvider")
    void shouldReturnProductOrThrowException(String productId, boolean shouldExistInFindByProduct) {
        if (shouldExistInFindByProduct) {
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            Product foundProduct = productService.findByProduct(productId);
            assertNotNull(foundProduct);
            assertEquals(product.getId(), foundProduct.getId());
            assertEquals(product.getName(), foundProduct.getName());
            assertEquals(product.getDescription(), foundProduct.getDescription());
            assertEquals(product.getPrice(), foundProduct.getPrice());
        } else {
            when(productRepository.findById(productId)).thenReturn(Optional.empty());
            CoreRuleException exception = assertThrows(CoreRuleException.class, () -> {
                productService.findByProduct(productId);
            });
            assertEquals(MessageKey.PRODUCT_NOT_FOUND.getCode(), exception.getMessageError().getCode());
        }
    }

    static Stream<Arguments> productDataProvider() {
        return Stream.of(
                Arguments.of("product123", true), // Caso onde o produto existe
                Arguments.of("unknownProduct", false) // Caso onde o produto não existe
        );
    }
}
