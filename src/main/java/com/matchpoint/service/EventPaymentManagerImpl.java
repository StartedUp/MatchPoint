package com.matchpoint.service;

import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.*;
import com.matchpoint.repository.EventPaymentRepository;
import com.matchpoint.repository.PaymentRepository;
import com.matchpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Prithu on 23/9/17.
 */
@Service
@Transactional
public class EventPaymentManagerImpl implements EventPaymentManager{
    @Autowired
    private EventPaymentRepository paymentRepository;
    @Autowired
    private FeeService feeService;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private OnlinePaymentProcessor onlinePaymentProcessor;

    @Override
    public EventPayment findById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }
    public EventPayment saveOrUpdate(EventPayment payment) {
        return paymentRepository.save(payment);
    }

    public EventPayment findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
