package com.matchpoint.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 18/7/18.
 */
@Controller
public class ExceptionErrorController {

    @RequestMapping("/exceptionError")
    public String showErrorPage(){
        return "exceptionError";
    }
}

