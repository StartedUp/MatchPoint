package com.matchpoint.controllers.admin;

import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Fee;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import com.matchpoint.service.FeeService;
import com.matchpoint.service.PaymentManager;
import com.matchpoint.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @Autowired
    private SessionUtil sessionUtil;

    /**
     * @author Balaji
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view-payment/{id}")
    public String viewPaymentDetails(@PathVariable int id, Model model){
        LOGGER.info("/view-payment : id : {}",id);
        List<Payment> payments = userManager.findOne(id).getPayments();
        Map<String, List<Payment>> paymentsMap = payments.stream().collect(Collectors.groupingBy(payment -> payment.getFee().getFeeName()));
        model.addAttribute("paymentsList", paymentsMap);
        model.addAttribute("id", id);
        return"/payment/payment-details";
    }


    @GetMapping("/create-payment/{id}")
    public String showCreatePayment(@PathVariable int id, Model model){
        User user = userManager.findOne(id);
        model.addAttribute("user",user);
        model.addAttribute("feeList",feeService.findByPlayerCategoryId(user.getPlayerCategory().getId()));
        return "/payment/createPayment";
    }

    @PostMapping("/create-pay")
    public String createPayment(@ModelAttribute("fee") int id, Model model){
        LOGGER.info("/pay : id : {}",id);
        Fee fee = feeService.getFeeById(id);
        User user = sessionUtil.getCurrentuser();
        String transactionId = ""+user.getEmail().hashCode()+new Date().getTime();
        Payment payment = new Payment();
        payment.setFee(fee).setAmount(fee.getAmount())
                .setPaymentDate(new Date()).setDescription(fee.getFeeName())
                .setPaymentMode(PaymentModeEnum.CASH.getDescription())
                .setUser(user)
                .setPaymentStatus(PaymentStatusEnum.SUCCESS.getStatus())
                .setTransactionId(transactionId);
        paymentManager.saveOrUpdate(payment);
        return "/payment/payment-details";
    }
}
