package com.matchpoint.service;

import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Fee;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 2/8/18.
 */
@Service
public class UserService {

    @Autowired
    private FeeService feeService;

    @Value("${payment.create.onregistration}")
    private boolean createRegistrationPayment;
    @Autowired
    private SessionUtil sessionUtil;

    public void populateDefaultValues(User user) {
        user.setActive(true);
        user.setAdminApproved(true);
        if (createRegistrationPayment)
            user.setPayments(createPayments(user));
    }

    private List<Payment> createPayments(User user) {
        Payment payment = new Payment();
        List<Fee> feeList = feeService.findByPlayerCategoryId(user.getPlayerCategory().getId());
        Optional<Fee> registration = feeList.stream().filter(fee -> fee.getFeeName() != null && fee.getFeeName().equals("Registration")).findFirst();
        if (registration.isPresent()) {
            payment.setDescription(registration.get().getDescription());
            payment.setTransactionId(new Date().getTime()+""+(user.getEmail()!=null?user.getEmail().hashCode()+"":""));
            payment.setAmount(registration.get().getAmount());
            payment.setPaymentMode(PaymentModeEnum.CASH.getDescription());
            payment.setPaymentStatus(PaymentStatusEnum.SUCCESS.getStatus());
            payment.setUser(user);
            payment.setFee(registration.get());
            payment.setPaymentDate(new Date());
        }
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(payment);
        return payments;
    }

    public List<Fee> getRelavantFeeList() {
        User user = sessionUtil.getCurrentuser();
        List<Fee> fees = feeService.findByPlayerCategoryId(user.getPlayerCategory().getId());
        List<Payment> payments = user.getPayments();
        if (payments!=null && !payments.isEmpty()) {
            Optional<Payment> registration = payments.stream().filter(payment -> payment.getFee().getFeeName().equals("Registration") && payment.getPaymentStatus().equals(PaymentStatusEnum.SUCCESS.getStatus())).findAny();
            if (registration.isPresent() || !user.isAdminApproved()) {
                fees = fees.stream().filter(fee -> fee.getId()!=registration.get().getFee().getId()).collect(Collectors.toList());
            }
            Optional<Payment> monthly = payments.stream().filter(payment -> {
                Date paymentDate = payment.getPaymentDate();
                if (paymentDate==null)
                    return false;
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(paymentDate);
                cal2.setTime(new Date());
               return payment.getFee().getFeeName().equals("Monthly") && payment.getPaymentStatus().equals(PaymentStatusEnum.SUCCESS.getStatus()) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            }).findAny();

            if (monthly.isPresent()) {
                fees = fees.stream().filter(fee -> fee.getId()!=monthly.get().getFee().getId()).collect(Collectors.toList());
            }
        }
        return fees;
    }
}
