package com.matchpoint.controllers;

import com.matchpoint.model.User;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "ListUsers";
    }
}
