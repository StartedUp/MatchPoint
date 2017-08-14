package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.Role;
import com.matchpoint.model.User;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.EventRegistrationManager;
import com.matchpoint.service.RoleManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private EventRegistrationManager eventRegistrationManager;

    /*@PreAuthorize("hasAnyRole('admin')")*/
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage(){
        return "adminHome";
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public String showUsers(Model model){
        List<User> users;
        users = userManager.findAll();
        Role role=roleManager.findByName("admin");
        model.addAttribute("users", users);
        model.addAttribute("roleAdmin",role);
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
        List<Event> events; List<EventRegistration> eventRegistrations;List<String> usersCount;
        events = eventManager.findAll();
        usersCount = eventRegistrationManager.registrationCount(events);
        model.addAttribute("events", events);
        model.addAttribute("registrationCount",usersCount);
        return "listEventsAsAdmin";
    }
    @GetMapping("/eventRegistrationList/{eventId}")
    public String showRegisteredUsersList(Model model, @PathVariable("eventId") Integer eventId){
        List<EventRegistration> eventRegistrations;
        Event event=eventManager.findById(eventId);
        eventRegistrations=eventRegistrationManager.findByEvent_id(eventId);
        model.addAttribute("eventRegistrations",eventRegistrations);
        model.addAttribute("event", event);
        return "registeredUsers";
    }
    @RequestMapping("/manageRoles")
    public String adminAccess(@RequestParam("userEmail") String email,@RequestParam("action") String action, Model model){
            userManager.manageAdminAccess(email, action);
           /* model.addAttribute("RolesModified",action+" Done");*/
            return "redirect:/a/listUsers";
    }
    @RequestMapping("/uploadAlbum")
    public String showUploadAlbumpage(){return "uploadAlbum";};

}
