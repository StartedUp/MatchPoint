package com.matchpoint.controllers;

import com.matchpoint.model.User;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by root on 5/7/17.
 */
@Controller
public class RegisterController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user")User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userManager.save(user);
        return "redirect:/";
    }
}
