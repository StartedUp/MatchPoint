package com.matchpoint.controllers.admin;

import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Payment;
import com.matchpoint.service.FeeService;
import com.matchpoint.service.PaymentManager;
import com.matchpoint.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Balaji on 31/7/18.
 */
@Controller
public class PaymentController extends AdminRootController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    UserManager userManager;
    @Autowired
    PaymentManager paymentManager;
    @Autowired
    FeeService feeService;

    @RequestMapping("/view-payment/{id}")
    public String viewPaymentDetails(@PathVariable int id, Model model){
        LOGGER.info("/view-payment : id : {}",id);
        List<Payment> payments = userManager.findOne(id).getPayments();
        payments=payments.stream().filter(payment -> payment.getPaymentStatus().equals(PaymentStatusEnum.SUCCESS.getStatus())).collect(Collectors.toList());
        Map<String, List<Payment>> paymentsMap = payments.stream().collect(Collectors.groupingBy(payment -> payment.getFee().getFeeName()));
        model.addAttribute("paymentsList", paymentsMap);
        return"/payment/payment-details";
    }
}
