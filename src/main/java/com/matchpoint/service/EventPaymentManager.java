package com.matchpoint.service;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventPayment;
import com.matchpoint.model.FeeListWrapper;
import com.matchpoint.model.Payment;

/**
 * Created by Prithu on 23/9/17.
 */
public interface EventPaymentManager {
    public EventPayment findById(int paymentId);
    public EventPayment findByTransactionId(String transactionId);
    public EventPayment saveOrUpdate(EventPayment payment);
}
