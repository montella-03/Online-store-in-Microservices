package com.ecommerce.productService.service;

import com.ecommerce.productService.model.ProductRecord;
import com.ecommerce.productService.model.ProductResponse;

public interface ProductService {
    Long getProductId(ProductRecord productRecord);
    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, Long quantity);

    void reStockQuantity(Long productId, Long quantity);
}
