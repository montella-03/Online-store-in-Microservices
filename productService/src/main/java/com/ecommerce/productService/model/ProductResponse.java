package com.ecommerce.productService.model;

public record ProductResponse
        (String productName,Long productId,Long price,Long quantity) {
}
