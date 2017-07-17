package com.matchpoint.repository;

import com.matchpoint.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by gokul on 16/7/17.
 */
public interface EventRepository extends JpaRepository<Event,Integer> {
    public List<Event> findAllByOrderByIdDesc();
    public List<Event> findByEndDateAfter(Date endDate);
}
