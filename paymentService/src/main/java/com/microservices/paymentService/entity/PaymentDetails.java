package com.microservices.paymentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "paymentDetails")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ORDER_ID")
    private Long orderId;
    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    @Column(name = "MPESA_CODE")
    private String referenceCode;
    @Column(name = "STATUS")
    private String paymentStatus;
    @Column(name = "PAYMENT_DATE")
    private Instant paymentDate;
    @Column(name = "AMOUNT")
    private Long amount;
}
