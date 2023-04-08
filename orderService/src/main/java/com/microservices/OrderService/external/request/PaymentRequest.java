package com.microservices.OrderService.external.request;

import com.microservices.OrderService.model.PaymentMode;

public record PaymentRequest
        (Long orderId, Long amount, String mpesaCode, PaymentMode paymentMode) {
}
