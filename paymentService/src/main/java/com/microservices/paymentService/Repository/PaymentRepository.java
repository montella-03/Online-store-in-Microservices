package com.microservices.paymentService.Repository;

import com.microservices.paymentService.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails,Long> {
    @Query("select u from PaymentDetails u where u.orderId = ?1")
    Optional<PaymentDetails> findByOrderId(Long orderId);
}
