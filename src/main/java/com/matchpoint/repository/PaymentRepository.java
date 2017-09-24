package com.matchpoint.repository;

import com.matchpoint.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Prithu on 23/9/17.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    public Payment findById(int paymentId);
    public Payment findByTransactionId(String transactionId);
}
