package com.microservices.OrderService.external.client;

import com.microservices.OrderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@CircuitBreaker(name="circuit")
@FeignClient(name = "paymentService/payments")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> doPayments(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/{orderId}")
    public PaymentRequest getPayment(@PathVariable Long orderId);


}
