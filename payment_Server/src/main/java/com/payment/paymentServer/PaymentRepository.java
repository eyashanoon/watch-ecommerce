package com.payment.paymentServer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByCardNumber(String cardNumber);
}
