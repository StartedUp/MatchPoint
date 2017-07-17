package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.model.User;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gokul on 10/7/17.
 */
@Controller
@RequestMapping(value = "/a")
public class AdminController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private EventManager eventManager;

    /*@PreAuthorize("hasAnyRole('admin')")*/
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage(){
        return "adminHome";
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public String showUsers(Model model){
        List<User> users;
        users = userManager.findAll();
        model.addAttribute("users", users);
        return "listUsers";
    }
    @GetMapping("/createEvent")
    public String createEvent(){
        return "createEvent";
    }
    @RequestMapping(value = "/registerEvent", method = RequestMethod.POST)
    public String register(@ModelAttribute("event")Event event, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            System.out.println(event.toString());
            System.out.println(bindingResult);
            return "createEvent";
        }
        eventManager.save(event);
        model.addAttribute("eventRegisterSuccess",true);
        return "redirect:/a/listEvents";
    }
    @RequestMapping(value = "/listEvents", method = RequestMethod.GET)
    public String showEvents(Model model){
        List<Event> events;
        events = eventManager.findAll();
        model.addAttribute("events", events);
        return "listEvents";
    }
}
