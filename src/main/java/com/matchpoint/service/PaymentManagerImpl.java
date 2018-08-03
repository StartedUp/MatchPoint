package com.matchpoint.service;

import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Fee;
import com.matchpoint.model.FeeListWrapper;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
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
public class PaymentManagerImpl implements PaymentManager{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private FeeService feeService;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private OnlinePaymentProcessor onlinePaymentProcessor;

    @Override
    public Payment findById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findByUserId(int userId) {
        return paymentRepository.findByUserId(userId);
    }

    public Payment saveOrUpdate(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public String processPayment(FeeListWrapper feeListWrapper) {
        List<Fee> feeList = feeListWrapper.getFeeList();
        List<Payment> payments = new ArrayList<>();
        User user = sessionUtil.getCurrentuser();
        String transactionId = ""+user.getEmail().hashCode()+new Date().getTime();
        feeList.forEach(fee -> {
            Payment payment = new Payment();
            Fee fee1 = feeService.getFeeById(fee.getId());
            payment.setFee(fee1);
            payment.setPaymentDate(new Date());
            payment.setPaymentMode(PaymentModeEnum.ONLINE.getDescription())
                    .setPaymentDate(new Date()).setUser(user)
                    .setDescription(fee1.getFeeName())
                    .setAmount(fee1.getAmount())
                    .setPaymentStatus(PaymentStatusEnum.INIT.getStatus());
            payment.setTransactionId(transactionId);
//            payment=paymentRepository.save(payment);
            payments.add(payment);
        });
        if (user.getPayments()!=null)
            payments.addAll(user.getPayments());
        user.setPayments(payments);
        userRepository.save(user);
        String redirectUrl = onlinePaymentProcessor.placeOrder(payments, transactionId);
        return redirectUrl!=null?"redirect:" +redirectUrl:"exceptionError";
    }
    public Payment findByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
