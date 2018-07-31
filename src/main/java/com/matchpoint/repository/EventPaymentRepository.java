package com.matchpoint.repository;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventPayment;
import com.matchpoint.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Prithu on 23/9/17.
 */
public interface EventPaymentRepository extends JpaRepository<EventPayment, Integer>{
    public EventPayment findById(int paymentId);
    public EventPayment findByTransactionId(String transactionId);
}
