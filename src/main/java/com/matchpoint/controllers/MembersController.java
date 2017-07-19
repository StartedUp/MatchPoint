package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.model.LoggedinUser;
import com.matchpoint.model.User;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    EventManager eventManager;
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
                 System.out.println("logged in email is "+email);
                 User user = userManager.findByEmail(email);
                 System.out.println(user.toString());
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
    @GetMapping("/registerEvent")
    public String showRegisterEventPage(){
        return "registerEvent";
    }

}
