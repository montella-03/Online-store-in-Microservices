package com.microservices.paymentService.service;

import com.microservices.paymentService.Repository.PaymentRepository;
import com.microservices.paymentService.entity.PaymentDetails;
import com.microservices.paymentService.model.PaymentMode;
import com.microservices.paymentService.model.PaymentRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Long dpPayments(PaymentRequest paymentRequest) {
        log.info("paying...");
        PaymentDetails paymentDetails=PaymentDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.paymentMode().name())
                .amount(paymentRequest.amount())
                .orderId(paymentRequest.orderId())
                .referenceCode(paymentRequest.mpesaCode())
                .paymentStatus("PAYED")
                .build();
        paymentRepository.save(paymentDetails);
        log.info("payed...");

        return paymentDetails.getId();
    }

    @Override
    public PaymentRequest getPayment(Long orderId) {
        PaymentDetails paymentDetails =
                paymentRepository.findByOrderId(orderId).orElseThrow(
                        ()->new RuntimeException("payment not found"));
        return new PaymentRequest(paymentDetails.getOrderId(),paymentDetails.getAmount(),
                paymentDetails.getReferenceCode(), PaymentMode.M_PESA);
    }
}
