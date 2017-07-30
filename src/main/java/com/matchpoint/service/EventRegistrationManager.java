package com.matchpoint.service;

import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
public interface EventRegistrationManager {
    public void save(EventRegistration eventRegistration);
    public List<EventRegistration> findByUser(User user);
}
