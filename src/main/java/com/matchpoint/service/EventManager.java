package com.matchpoint.service;

import com.matchpoint.model.Event;
import com.matchpoint.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by gokul on 16/7/17.
 */
public interface EventManager {
    public Event save(Event event);
    public List<Event> findAll();
    public List<Event> findByEndDateAfter(Date endDate);
    public Event findById(Integer eventId);
    List<Event> findAllByOrderByEndDateDesc();

}
