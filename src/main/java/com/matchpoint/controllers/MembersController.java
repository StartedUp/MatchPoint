package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    EventManager eventManager;
    @GetMapping("/home")
    public String showMemberHome(){
        return "memberHome";
    }

}
