package com.matchpoint.controllers;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.matchpoint.model.*;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
    @Value("${payment.online.instamojo.api.endpoint}")
    private String instamojoApiEndpoint;
    @Value("${payment.online.instamojo.auth.endpoint}")
    private String instamojoAuthEndpoint;
    @Value("${payment.online.instamojo.client.id}")
    private String instamojoClientId;
    @Value("${payment.online.instamojo.client.secret}")
    private String instamojoClientSecret;

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
        Payment payment=new Payment();
        model.addAttribute("payment",payment);
        return "paymentPage";
    }
    @PostMapping("/payment/{productId}")
    public String payFee(
            @ModelAttribute("payment")Payment payment, @PathVariable("productId")int productId) {
        try {
            String paymentUrl = null;
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Product product = productManager.findById(productId);
            payment.setPaymentMode("online")
                    .setPaymentDate(new Date()).setProduct(product).setUser(user)
                    .setDescription(product.getDescription())
                    .setPaymentStatus("initialized");
            payment=paymentManager.saveOrUpdate(payment);
            payment.setTransactionId(environment.getProperty("payment.online.transaction.id")+payment.getId());
            payment=paymentManager.saveOrUpdate(payment);
            paymentUrl = onlinePaymentProcessor.placeOrder(payment);
            return paymentUrl != null ? "redirect:" + paymentUrl : "exceptionError";
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
