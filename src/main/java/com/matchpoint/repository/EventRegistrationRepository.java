package com.matchpoint.repository;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Integer> {
    public List<EventRegistration> findAllByOrderByIdDesc();
    public List<EventRegistration> findByUser(User user);
    public List<EventRegistration> findByEvent_id(int eventid);
    public EventRegistration findByEventAndUser(Event event,User user);
}
