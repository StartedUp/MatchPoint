package com.matchpoint.repository;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.Payment;
import com.matchpoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
 public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Integer> {
     List<EventRegistration> findAllByOrderByIdDesc();
     List<EventRegistration> findByUser(User user);
     List<EventRegistration> findByEvent_id(int eventid);
     EventRegistration findByEventAndUser(Event event,User user);
    EventRegistration findByPayment(Payment payment);
}
