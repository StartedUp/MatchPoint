package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.service.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.List;

/**
 * Created by gokul on 16/7/17.
 */
@Controller
public class WelcomeController {
   @Autowired
   private EventManager eventManager;
    @GetMapping("/events")
    public String showMemberHome(Model model){
        List<Event> events;
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        events = eventManager.findByEndDateAfter(cal.getTime());
        model.addAttribute("events", events);
        return "listEvents";
    }
}
