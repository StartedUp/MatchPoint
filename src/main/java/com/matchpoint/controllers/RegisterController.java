package com.matchpoint.controllers;

import com.matchpoint.model.Role;
import com.matchpoint.model.User;
import com.matchpoint.service.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by root on 5/7/17.
 */
@Controller
public class RegisterController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class.getName());
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        User userExists = userManager.findByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Email already exist");
        }
        if (bindingResult.hasErrors()){
            LOGGER.info(bindingResult+"");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role role = new Role();
        LOGGER.info(user.toString());
        userManager.save(user);
        /*userManager.grantMemberAccess(user.getEmail());*/
        model.addAttribute("registerSuccess",true);
        return "redirect:/login";
    }
}
