package com.microservices.OrderService.model;

public record OrderRequest
        (Long productId,Long quantity,PaymentMode paymentMode) {

}
