package com.matchpoint.service;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.User;
import com.matchpoint.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gokul on 16/7/17.
 */
@Service
@Transactional
public class EventManagerImpl implements EventManager {

    @Autowired
    EventRepository eventRepository;
    @Override
    public Event save(Event event){
       return eventRepository.save(event);
    }
    @Override
    public List<Event> findAll(){ return eventRepository.findAllByOrderByIdDesc(); }
    @Override
    public List<Event> findByEndDateAfter(Date endDate) {
        return eventRepository.findByEndDateAfter(endDate);
    }
}
