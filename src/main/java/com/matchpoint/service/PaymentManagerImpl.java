package com.matchpoint.service;

import com.matchpoint.model.Payment;
import com.matchpoint.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Prithu on 23/9/17.
 */
@Service
@Transactional
public class PaymentManagerImpl implements PaymentManager{
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment findById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }
    public Payment saveOrUpdate(Payment payment) {
        return paymentRepository.save(payment);
    }
    public Payment findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
