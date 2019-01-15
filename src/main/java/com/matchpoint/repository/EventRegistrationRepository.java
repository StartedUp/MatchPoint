package com.matchpoint.repository;

import com.matchpoint.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Prithu on 30/7/17.
 */
 public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Integer> {
    List<EventRegistration> findAllByOrderByIdDesc();
    List<EventRegistration> findByUser(User user);
    List<EventRegistration> findByEvent_id(int eventid);
    EventRegistration findByEventAndUser(Event event,User user);
    EventRegistration findByEventPayment(EventPayment payment);
    EventRegistration findByEvent_idAndPlayerEmail(int id, String playerEmail);
    @Query("SELECT e FROM EventRegistration e join e.playingCategories p WHERE p.id = :playingCatId and e.event.id=:eventid")
    List<EventRegistration> findByEvent_PlayingCategory(@Param("playingCatId") int playingCatId, @Param("eventid") int eventid);

}
