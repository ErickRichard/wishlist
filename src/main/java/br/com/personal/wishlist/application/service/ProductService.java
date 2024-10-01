package br.com.personal.wishlist.application.service;

import br.com.personal.wishlist.application.dto.response.ProductResponse;
import br.com.personal.wishlist.application.port.ProductServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import br.com.personal.wishlist.domain.mapper.ProductMapper;
import br.com.personal.wishlist.domain.model.Product;
import br.com.personal.wishlist.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductServicePort {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductResponse findProduct(String id) {
        var product = productRepository.findById(id).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.PRODUCT_NOT_FOUND)));
        return productMapper.toResponse(product);
    }

    @Override
    public Product findByProduct(String id) {
        return productRepository.findById(id).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.PRODUCT_NOT_FOUND)));
    }
}
