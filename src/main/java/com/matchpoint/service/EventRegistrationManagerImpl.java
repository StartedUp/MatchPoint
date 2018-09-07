package com.matchpoint.service;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.matchpoint.Util.SessionUtil;
import com.matchpoint.controllers.EventRegistrationController;
import com.matchpoint.enums.PaymentModeEnum;
import com.matchpoint.enums.PaymentStatusEnum;
import com.matchpoint.model.*;
import com.matchpoint.repository.EventRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Prithu on 30/7/17.
 */
@Service
@Transactional
public class EventRegistrationManagerImpl implements EventRegistrationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventRegistrationManagerImpl.class.getName());
    @Value("${payment.online.instamojo.api.endpoint}")
    private String instamojoApiEndpoint;
    @Value("${payment.online.instamojo.auth.endpoint}")
    private String instamojoAuthEndpoint;
    @Value("${payment.online.instamojo.client.id}")
    private String instamojoClientId;
    @Value("${payment.online.instamojo.client.secret}")
    private String instamojoClientSecret;
    @Value("${payment.ignore.pg}")
    private boolean ignorePg;

    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;
    @Autowired
    private PaymentManager paymentManager;
    @Autowired
    private EventPaymentManager eventPaymentManager;
    @Autowired
    private OnlinePaymentProcessor onlinePaymentProcessor;

    @Override
    public void save(EventRegistration eventRegistration) {
        eventRegistrationRepository.save(eventRegistration);
    }
    @Override
    public List<EventRegistration> findAll(){ return eventRegistrationRepository.findAllByOrderByIdDesc(); }
    @Override
    public List<EventRegistration> findByUser(User user) {
        return eventRegistrationRepository.findByUser(user);
    }
    @Override
    public List<String> registrationCount(List<Event> eventList) {
        List<String> usersCount=new ArrayList<String>();
        for (Event event:eventList) {
           int eventId;
            eventId=event.getId();
            System.out.println(event.toString());
            System.out.println("event id is "+eventId);
            usersCount.add(eventRegistrationRepository.findByEvent_id(eventId).size()+"");
        }
        return usersCount;
    }

    @Override
    public List<EventRegistration> findByEvent_id(Integer eventId) {
        return eventRegistrationRepository.findByEvent_id(eventId);
    }

    @Override
    public EventRegistration findByEventAndUser(Event event, User user) {
        return eventRegistrationRepository.findByEventAndUser(event,user);
    }

    @Override
    public String  processAndRegister(EventRegistration eventRegistration) {
        if(eventRegistration!=null) {
            LOGGER.info("procession event registration : {}", eventRegistration.getEvent().getName());
            if (sessionUtil.isAuthenticated()) {
                eventRegistration.setUser(sessionUtil.getCurrentuser());
            }
            eventRegistration=eventRegistrationRepository.save(eventRegistration);
            Event event = eventRegistration.getEvent();
            List<PlayingCategory> playingCategories = eventRegistration.getPlayingCategories();
            int totalFee = playingCategories.stream().mapToInt(playingCategory -> playingCategory.getFee().intValue()).sum();
            EventPayment payment = new EventPayment();
            if (!ignorePg) {
                payment.setAmount(new BigDecimal(totalFee)).setDescription(event.getName()).setPaymentDate(new Date())
                        .setPaymentStatus(PaymentStatusEnum.INIT.getStatus()).setPaymentMode(PaymentModeEnum.ONLINE.getMode()+"");
                eventPaymentManager.saveOrUpdate(payment);
                eventRegistration.setEventPayment(payment);
                eventRegistration=eventRegistrationRepository.save(eventRegistration);
            } else {
                payment.setAmount(new BigDecimal(totalFee)).setDescription(event.getName()).setPaymentDate(new Date())
                        .setPaymentStatus(PaymentStatusEnum.PENDING.getStatus()).setPaymentMode(PaymentModeEnum.OFFLINE.getMode()+"");
                eventPaymentManager.saveOrUpdate(payment);
                eventRegistration.setEventPayment(payment);
                eventRegistrationRepository.save(eventRegistration);
                return "redirect:/?paymentStatus="+PaymentStatusEnum.SUCCESS.getStatus();
            }
            return processPayment(eventRegistration);
        }
        return "exceptionError";
    }

    private String processPayment(EventRegistration eventRegistration) {
        try {
            EventPayment payment = eventRegistration.getEventPayment();
            String paymentUrl = null;
            payment.setPaymentDate(new Date())
                    .setTransactionId(eventRegistration.getId()+""+eventRegistration.getEvent().getId()+eventRegistration.getPlayerEmail().hashCode());
            payment=eventPaymentManager.saveOrUpdate(payment);
            paymentUrl = onlinePaymentProcessor.payForEventRegistration(eventRegistration);
            return paymentUrl != null ? paymentUrl : "exceptionError";
        } catch (Exception e) {
            e.printStackTrace();
            return "exceptionError";
        }
    }

    @Override
    public EventRegistration findByPayment(EventPayment payment) {
        return eventRegistrationRepository.findByEventPayment(payment);
    }

    @Override
    public boolean validateRegistration(EventRegistration eventRegistration, Model model) {
        eventRegistration=eventRegistrationRepository.findByEvent_idAndPlayerEmail(eventRegistration.getEvent().getId(), eventRegistration.getPlayerEmail());
        if (eventRegistration!=null && eventRegistration.getEventPayment().getPaymentStatus().equals(PaymentStatusEnum.SUCCESS.getStatus())) {
            model.addAttribute("eventRegistration", eventRegistration);
            model.addAttribute("alreadyRegistered", true);
            return false;
        }
        return true;
    }
}
