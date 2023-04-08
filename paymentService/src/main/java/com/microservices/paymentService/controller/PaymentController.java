package com.microservices.paymentService.controller;

import com.microservices.paymentService.model.PaymentRequest;
import com.microservices.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayments(@RequestBody PaymentRequest paymentRequest){
        Long paymentId = paymentService.dpPayments(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentRequest> getPayment(@PathVariable Long orderId){
        PaymentRequest request = paymentService.getPayment(orderId);
        return new ResponseEntity<>(request,HttpStatus.OK);
    }

}
