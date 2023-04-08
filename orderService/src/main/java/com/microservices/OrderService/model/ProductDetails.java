package com.microservices.OrderService.model;

public  record ProductDetails
        (String productName,Long productId,Long price,Long quantity) {
}
