package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.LoggedinUser;
import com.matchpoint.model.User;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.EventRegistrationManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gokul on 5/7/17.
 */
@Controller
@RequestMapping(value = "/u")
public class MembersController {
    @Autowired
    private UserManager userManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EventManager eventManager;
    @Autowired
    private EventRegistrationManager eventRegistrationManager;
    @GetMapping("/home")
    public String showMemberHome(){
        return "memberHome";
    }
    @GetMapping("/userProfile")
    public String showUserProfile(){
        return "userProfile";
    }
    @GetMapping("/changePassword")
    public String changePassword(){
        return "changePassword";
    }
    @RequestMapping("/updatePassword")
    public String changePassword(@RequestParam("password") String password,Model model) {
        System.out.println(password);
        try {
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             if (auth.isAuthenticated()) {
                 String email=auth.getName();
                 User user = userManager.findByEmail(email);
                if (password!=null && !password.equals("")) {
                    user.setPassword(passwordEncoder.encode(password));
                    userManager.updatePassword(email,user.getPassword());
                    SecurityContextHolder.getContext().setAuthentication(null);
                    model.addAttribute("passwordChangeSuccess", true);
                }
                    return "redirect:/login";
             } else
                return "redirect:/login";
        } catch (Exception e) {
        e.printStackTrace();
        return "exceptionError";}
    }
    @GetMapping("/eventRegistration/{eventId}")
    public String showRegisterEventPage(Model model, @PathVariable("eventId") Integer eventId){
        EventRegistration eventRegistration=null;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userManager.findByEmail(user.getEmail());
        Event event=eventManager.findById(eventId);
        eventRegistration=eventRegistrationManager.findByEventAndUser(event,user);
        model.addAttribute("eventRegistration", eventRegistration!=null?eventRegistration:new EventRegistration());
        model.addAttribute("event",event);
        model.addAttribute("currentUser", currentUser);
        return "registerEvent";
    }
    @RequestMapping(value = "/registerEvent",method = RequestMethod.POST)
    public String registerEvent(@ModelAttribute("eventRegistration") EventRegistration eventRegistration, BindingResult
                                bindingResult, Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userManager.findByEmail(user.getEmail());
        eventRegistration.setUser(currentUser);
        eventRegistration.setUserDob(currentUser.getDob());
        if (bindingResult.hasErrors()){
            return "registerEvent";
        }
        eventRegistrationManager.save(eventRegistration);
        return "redirect:/u/myRegisteredEvents";
    }
    @GetMapping("/myRegisteredEvents")
    public String showMyEvents(Model model) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EventRegistration> eventRegistrations = eventRegistrationManager.findByUser(user);
        List<Event> events=new ArrayList<Event>();
        for (EventRegistration eventRegistration: eventRegistrations) {
            events.add(eventRegistration.getEvent());
        }
        model.addAttribute("registeredEvents",events);
        return "myEvents";
    }
}
