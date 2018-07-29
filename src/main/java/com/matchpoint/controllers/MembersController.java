package com.matchpoint.controllers;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.Fee;
import com.matchpoint.model.FeeListWrapper;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import com.matchpoint.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gokul on 5/7/17.
 */
@Controller
@RequestMapping(value = "/u")
public class MembersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinePaymentProcessor.class.getName());
    @Autowired
    private Environment environment;
    @Autowired
    private UserManager userManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EventManager eventManager;
    @Autowired
    private EventRegistrationManager eventRegistrationManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private OnlinePaymentProcessor onlinePaymentProcessor;
    @Autowired
    private PaymentManager paymentManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private SessionUtil sessionUtil;
    @Value("${payment.online.instamojo.api.endpoint}")
    private String instamojoApiEndpoint;
    @Value("${payment.online.instamojo.auth.endpoint}")
    private String instamojoAuthEndpoint;
    @Value("${payment.online.instamojo.client.id}")
    private String instamojoClientId;
    @Value("${payment.online.instamojo.client.secret}")
    private String instamojoClientSecret;
    @Autowired
    private FeeService feeService;


    @GetMapping("/home")
    public String showMemberHome(){
        return "memberHome";
    }
    @GetMapping("/userProfile")
    public String showUserProfile(){
        return "userProfile";
    }
    @GetMapping("/changePassword")
    public String changePassword(){
        return "changePassword";
    }
    @RequestMapping("/updatePassword")
    public String changePassword(@RequestParam("password") String password,Model model) {
        System.out.println(password);
        try {
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             if (auth.isAuthenticated()) {
                 String email=auth.getName();
                 User user = userManager.findByEmail(email);
                if (password!=null && !password.equals("")) {
                    user.setPassword(passwordEncoder.encode(password));
                    userManager.updatePassword(email,user.getPassword());
                    SecurityContextHolder.getContext().setAuthentication(null);
                    model.addAttribute("passwordChangeSuccess", true);
                }
                    return "redirect:/login";
             } else
                return "redirect:/login";
        } catch (Exception e) {
        e.printStackTrace();
        return "exceptionError";}
    }
    @GetMapping("/payment")
    public String showPaymentPage(Model model) {
        User user = sessionUtil.getCurrentuser();
        List<Fee> feeList = feeService.findByPlayerCategoryId(user.getPlayerCategory().getId());
        Payment payment=new Payment();
        List<Payment> payments = user.getPayments();
        model.addAttribute("includeRegistrationFee", false);
        if(payments==null || payments.isEmpty()){
            model.addAttribute("includeRegistrationFee", true);
        }
        else {
            boolean registration = payments.stream().anyMatch(payment1 ->
                    payment1.getFee()!=null && payment1.getFee()
                            .getFeeName().equals("Registration")
                            && payment1.getPaymentStatus().equals(PaymentStatusEnum.INIT.getStatus()));
            model.addAttribute("includeRegistrationFee", !registration);
        }
        model.addAttribute("payment",payment);
        model.addAttribute("feeList",feeList);
        model.addAttribute("user",user);
        return "paymentPage";
    }
    @PostMapping("/payment/pay")
    public String payFee(
            @ModelAttribute("feeList")FeeListWrapper feeListWrapper) {
        try {
            String paymentUrl = paymentManager.processPayment(feeListWrapper);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return paymentUrl != null ? "redirect:/" + paymentUrl : "exceptionError";
        } catch (Exception e) {
            e.printStackTrace();
            return "exceptionError";
        }
    }
    @GetMapping("/user/payment/paymentStatus")
    public String paymentSuccessRedirect(
            @RequestParam("id") String paymentRequestId,
            @RequestParam("transaction_id") String transactionId,
            @RequestParam("payment_id") String paymentId
    ) {
        try {
            Payment payment=paymentManager.findByTransactionId(transactionId);
            Instamojo api = InstamojoImpl.getApi(instamojoClientId, instamojoClientSecret, instamojoApiEndpoint, instamojoAuthEndpoint);

            PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(transactionId+"");
            // print the status of the payment order.
            LOGGER.info("paymentOrderDetailsResponse.getStatus() "+paymentOrderDetailsResponse.getStatus());
            String status =paymentOrderDetailsResponse.getStatus();
            if (status.equals("completed")){
                payment.setPaymentStatus(status);
                payment.setOrderId(paymentOrderDetailsResponse.getId());
                paymentManager.saveOrUpdate(payment);
                return "redirect:/?paymentSuccess";
            }
            else
                return "exceptionError";
        }catch (Exception e){
            LOGGER.info("Exception getting payment status", e);
            return "exceptionError";
        }
    }
}
