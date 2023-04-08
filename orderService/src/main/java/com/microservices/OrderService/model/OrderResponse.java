package com.microservices.OrderService.model;

import java.time.Instant;

public record OrderResponse
        (Long orderId, Long amount,Long quantity, Instant orderDate,
         String orderStatus,ProductDetail productDetail,PaymentDetails paymentDetails) {
    public record PaymentDetails(String code,Long amount){

    }
    public record ProductDetail(String name,Long price){

    }
}

