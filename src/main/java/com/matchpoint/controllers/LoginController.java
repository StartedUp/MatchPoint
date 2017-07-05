package com.matchpoint.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/u/all")
    public String securedHello() {
        return "Secured Hello";
    }

    @GetMapping("/secured/alternate")
    public String alternate() {
        return "alternate";
    }
}
