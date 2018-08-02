package com.matchpoint.repository;

import com.matchpoint.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Prithu on 23/9/17.
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    public Payment findById(int paymentId);
    public List<Payment> findByTransactionId(String transactionId);

    List<Payment> findByUserId(int userId);
}
