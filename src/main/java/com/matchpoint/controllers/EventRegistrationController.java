package com.matchpoint.controllers;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.matchpoint.Util.SessionUtil;
import com.matchpoint.controllers.admin.AdminRootController;
import com.matchpoint.enums.GenderTypeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.*;
import com.matchpoint.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 17/7/18.
 */
@Controller
public class EventRegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRegistrationController.class.getName());
    @Autowired
    private UserManager userManager;
    @Autowired
    private EventManager eventManager;
    @Autowired
    private EventRegistrationManager eventRegistrationManager;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private PaymentManager paymentManager;
    @Autowired
    private EventPaymentManager eventPaymentManager;
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

    @GetMapping("/eventRegistration/{eventId}")
    public String showRegisterEventPage(Model model, @PathVariable("eventId") Integer eventId){
        EventRegistration eventRegistration=null;
        Event event=eventManager.findById(eventId);
        User currentUser = null;
        if (sessionUtil.isAuthenticated()) {
            currentUser = sessionUtil.getCurrentuser();
            model.addAttribute("currentUser", currentUser);
        }
        //eventRegistration=eventRegistrationManager.findByEventAndUser(event,user);
        model.addAttribute("eventRegistration", eventRegistration!=null?eventRegistration:new EventRegistration());
        model.addAttribute("event",event).addAttribute("playingCategories", event.getPlayingCategories());
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        return "registerEvent";
    }
    @RequestMapping(value = "/registerEvent",method = RequestMethod.POST)
    public String registerEvent(@ModelAttribute("eventRegistration") EventRegistration eventRegistration, BindingResult
            bindingResult, Model model) {
        String paymentUrl= "/exceptionError";
        try {
            if (bindingResult.hasErrors()){
                model.addAttribute(eventRegistration.getEvent());
                model.addAttribute("playingCategories", eventRegistration.getEvent().getPlayingCategories());
                model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
                return "registerEvent";
            }
            if (!eventRegistrationManager.validateRegistration(eventRegistration, model)) {
                return "eventRegistration-success";
            }
            paymentUrl = eventRegistrationManager.processAndRegister(eventRegistration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:"+paymentUrl;
    }
    @GetMapping("/u/myRegisteredEvents")
    public String showMyEvents(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EventRegistration> eventRegistrations = eventRegistrationManager.findByUser(user);
        List<Event> events=new ArrayList<Event>();
        for (EventRegistration eventRegistration: eventRegistrations) {
            events.add(eventRegistration.getEvent());
        }
        model.addAttribute("registeredEvents",events);
        return "myEvents";
    }

    @GetMapping("/u/eventRegistration/paymentStatus")
    public String paymentSuccessRedirect(
            @RequestParam("id") String paymentRequestId,
            @RequestParam("transaction_id") String transactionId,
            @RequestParam("payment_id") String paymentId,
            HttpSession session
    ) {
        try {
            EventPayment payment=eventPaymentManager.findByTransactionId(transactionId);
            Instamojo api = InstamojoImpl.getApi(instamojoClientId, instamojoClientSecret, instamojoApiEndpoint, instamojoAuthEndpoint);

            PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(transactionId+"");
            // print the status of the payment order.
            LOGGER.info("paymentOrderDetailsResponse.getStatus() "+paymentOrderDetailsResponse.getStatus());
            String status =paymentOrderDetailsResponse.getStatus();
            if (status.equals("completed")){
                payment.setPaymentStatus(PaymentStatusEnum.SUCCESS.getStatus());
                payment.setOrderId(paymentOrderDetailsResponse.getId());
                eventPaymentManager.saveOrUpdate(payment);
                session.setAttribute("payment", payment);
                return "redirect:/eventRegistration/success";
            }
            else
                return "exceptionError";
        }catch (Exception e){
            LOGGER.info("Exception getting payment status", e);
            return "exceptionError";
        }
    }

    @RequestMapping("/u/eventRegistration/success")
    public String eventRegistrationSuccess(Model model, HttpSession session) {
        EventPayment payment = (EventPayment) session.getAttribute("payment");
        if(payment!=null){
            EventRegistration eventRegistration = eventRegistrationManager.findByPayment(payment);
            model.addAttribute("eventRegistration", eventRegistration);
            session.removeAttribute("payment");
            return "eventRegistration-success";
        }
        return "redirect:/";
    }

}
