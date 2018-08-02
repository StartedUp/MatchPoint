package com.matchpoint.service;

import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Fee;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by root on 2/8/18.
 */
@Service
public class UserService {

    @Autowired
    private FeeService feeService;

    @Value("${payment.create.onregistration}")
    private boolean createRegistrationPayment;

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
            payment.setTransactionId(new Date().getTime()+""+user.getEmail()!=null?user.getEmail().hashCode()+"":"");
            payment.setAmount(registration.get().getAmount());
            payment.setPaymentMode(PaymentModeEnum.CASH.getDescription());
            payment.setPaymentStatus(PaymentStatusEnum.SUCCESS.getStatus());
            payment.setUser(user);
            payment.setFee(registration.get());
        }
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(payment);
        return payments;
    }
}
