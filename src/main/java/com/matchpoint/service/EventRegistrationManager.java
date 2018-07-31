package com.matchpoint.service;

import com.matchpoint.model.*;
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

    EventRegistration findByPayment(EventPayment payment);
}
