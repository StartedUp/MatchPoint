package com.matchpoint.service;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
public interface EventRegistrationManager {

    void save(EventRegistration eventRegistration);

    List<EventRegistration> findAll();

    List<EventRegistration> findByUser(User user);

    List<EventRegistration> findByEvent_id(Integer eventId);

    List<String> registrationCount(List<Event> eventList);

    EventRegistration findByEventAndUser(Event event, User user);

    String processAndRegister(EventRegistration eventRegistration);

    EventRegistration findByPayment(Payment payment);
}
