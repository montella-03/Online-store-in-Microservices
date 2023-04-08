package com.microservices.paymentService.service;

import com.microservices.paymentService.model.PaymentRequest;

public interface PaymentService {
    Long dpPayments(PaymentRequest paymentRequest);

    PaymentRequest getPayment(Long orderId);
}
