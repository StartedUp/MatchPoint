package com.matchpoint.controllers;

import com.matchpoint.enums.GenderTypeEnum;
import com.matchpoint.model.User;
import com.matchpoint.service.PlayerCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Controller
public class LoginController {

    @Autowired
    private PlayerCategoryService playerCategoryService;

    @GetMapping("/")
    public String showHomePage(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                /* The user is logged in :) */
                return "memberHome";
            }
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            model.addAttribute("userNotFound", true);
            return "index";
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
    /* The user is logged in :) */
                return "memberHome";
            }
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            model.addAttribute("userNotFound", true);
            return "userLogin";
        }

        return "userLogin";
    }
    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "userLogin";
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        model.addAttribute("playerCategories", playerCategoryService.listplayerCategory());
        return "register";
    }

}
