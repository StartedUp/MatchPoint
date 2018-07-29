package com.matchpoint.service;

import com.matchpoint.model.FeeListWrapper;
import com.matchpoint.model.Payment;

/**
 * Created by Prithu on 23/9/17.
 */
public interface PaymentManager {
    public Payment findById(int paymentId);
    public Payment findByTransactionId(String transactionId);
    public Payment saveOrUpdate(Payment payment);
    String processPayment(FeeListWrapper feeListWrapper);
}
