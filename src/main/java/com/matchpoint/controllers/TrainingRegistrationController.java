package com.matchpoint.controllers;

import com.matchpoint.model.TrainingProgram;
import com.matchpoint.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Prithviprakash on 14/11/18.
 */
@Controller
public class TrainingRegistrationController {

    @Autowired
    private TrainingProgramService trainingProgramService;

    @GetMapping("/trainingProgramsToRegister")
    public String showMemberHome(Model model){
        List<TrainingProgram> trainingPrograms;
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        trainingPrograms = trainingProgramService.findByEndDateAfter(cal.getTime());
        model.addAttribute("trainingPrograms", trainingPrograms);
        return "trainingProgram/trainingProgramsForRegistration";
    }
}
