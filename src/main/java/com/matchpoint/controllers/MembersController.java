package com.matchpoint.controllers;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.matchpoint.Util.SessionUtil;
import com.matchpoint.enums.GenderTypeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
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

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PlayerCategoryService playerCategoryService;
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
    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String showMemberHome(){
        return "memberHome";
    }
    @GetMapping("/userProfile")
    public String showUserProfile(Model model){
        User user = sessionUtil.getCurrentuser();
        model.addAttribute("user", user);
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        model.addAttribute("playerCategories", playerCategoryService.listplayerCategory());
        return "userProfile";
    }
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        User userExists = userManager.findOne(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        model.addAttribute("playerCategories", playerCategoryService.listplayerCategory());
        if (userExists == null || (userExists!=null && userExists.getId()!=sessionUtil.getCurrentuser().getId())) {
           model.addAttribute("InvalidData", true);
        }
        user.setPassword(userExists.getPassword());
        user.setActive(userExists.isActive());
        user.setRoles(userExists.getRoles());
        user.setPayments(userExists.getPayments());
        user.setAdminApproved(userExists.isAdminApproved());
        user=userManager.save(user);
        model.addAttribute("user", user);
        model.addAttribute("updateSuccess",true);
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
        List<Fee> feeList = userService.getRelavantFeeList();
        model.addAttribute("feeList",feeList);
        model.addAttribute("user",sessionUtil.getCurrentuser());
        return "paymentPage";
    }
    @PostMapping("/payment/pay")
    public String payFee(
            @ModelAttribute("feeList")FeeListWrapper feeListWrapper) {
        try {
            String paymentUrl = paymentManager.processPayment(feeListWrapper);
            return "redirect:"+paymentUrl;
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
            List<Payment> payments=paymentManager.findByTransactionId(transactionId);
            Instamojo api = InstamojoImpl.getApi(instamojoClientId, instamojoClientSecret, instamojoApiEndpoint, instamojoAuthEndpoint);

            PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(transactionId+"");
            // print the status of the payment order.
            LOGGER.info("paymentOrderDetailsResponse.getStatus() "+paymentOrderDetailsResponse.getStatus());
            String status =paymentOrderDetailsResponse.getStatus();
            if (status.equals("completed")){
                payments.forEach(payment -> {
                    payment.setPaymentStatus(PaymentStatusEnum.SUCCESS.getStatus());
                    payment.setOrderId(paymentOrderDetailsResponse.getId());
                    paymentManager.saveOrUpdate(payment);
                });
                return "redirect:/?paymentStatus="+PaymentStatusEnum.SUCCESS.getStatus();
            }
            else {
                payments.forEach(payment -> {
                    payment.setPaymentStatus(PaymentStatusEnum.FAILED.getStatus());
                    payment.setOrderId(paymentOrderDetailsResponse.getId());
                    paymentManager.saveOrUpdate(payment);
                });
                return "redirect:/?paymentStatus="+PaymentStatusEnum.FAILED.getStatus();
            }
        }catch (Exception e){
            LOGGER.info("Exception getting payment status", e);
            return "exceptionError";
        }
    }
}
