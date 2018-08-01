package com.matchpoint.service;

import com.matchpoint.model.FeeListWrapper;
import com.matchpoint.model.Payment;

import java.util.List;

/**
 * Created by Prithu on 23/9/17.
 */
public interface PaymentManager {
    public Payment findById(int paymentId);
    public List<Payment> findByUserId(int userId);
    public Payment findByTransactionId(String transactionId);
    public Payment saveOrUpdate(Payment payment);
    String processPayment(FeeListWrapper feeListWrapper);
}
