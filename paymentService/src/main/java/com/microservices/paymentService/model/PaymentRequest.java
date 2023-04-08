package com.microservices.paymentService.model;

public record PaymentRequest
        (Long orderId,Long amount,String mpesaCode,PaymentMode paymentMode) {
}
