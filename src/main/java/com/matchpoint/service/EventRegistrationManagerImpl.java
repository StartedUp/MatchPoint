package com.matchpoint.service;

import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.User;
import com.matchpoint.repository.EventRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
@Service
@Transactional
public class EventRegistrationManagerImpl implements EventRegistrationManager {
    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;
    @Override
    public void save(EventRegistration eventRegistration) {
        eventRegistrationRepository.save(eventRegistration);
    }

    @Override
    public List<EventRegistration> findByUser(User user) {
        return eventRegistrationRepository.findByUser(user);
    }
}
