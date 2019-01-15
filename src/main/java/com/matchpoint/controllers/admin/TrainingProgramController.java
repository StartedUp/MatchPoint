package com.matchpoint.controllers.admin;

import com.matchpoint.enums.EventTypesEnum;
import com.matchpoint.model.TrainingProgram;
import com.matchpoint.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * Created by Prithviprakash on 12/11/18.
 */
@Controller
public class TrainingProgramController extends AdminRootController{

    @Autowired
    private TrainingProgramService trainingProgramService;

    @RequestMapping(value = "/trainingPrograms", method = RequestMethod.GET)
    public String listPlayingCategory(Model model) {
        model.addAttribute("trainingPrograms", this.trainingProgramService.listPlayingCategory());
        return "trainingProgram/trainingPrograms";
    }
    @RequestMapping("/trainingProgram/showCreatePage")
    public  String showCreatePage(Model model) {
        model.addAttribute("trainingProgram", new TrainingProgram())
                .addAttribute("eventTypes", EventTypesEnum.values());;
        return "trainingProgram/createTrainingProgram";
    }

    //For add and update TrainingProgram both
    @RequestMapping(value= "/trainingProgram/add", method = RequestMethod.POST)
    public String addTrainingProgram(@ModelAttribute("TrainingProgram") TrainingProgram trainingProgram, Model model){

        if(trainingProgram.getId() == 0){
            //new TrainingProgram, add it
            this.trainingProgramService.addPlayingCategory(trainingProgram);
            model.addAttribute("createSuccess", true);
        }else{
            //existing TrainingProgram, call update
            model.addAttribute("editSuccess", true);
            this.trainingProgramService.updatePlayingCategory(trainingProgram);
        }

        return "redirect:/a/trainingPrograms";

    }

    @RequestMapping("/trainingProgram/remove/{id}")
    public String removeTrainingProgram(@PathVariable("id") int id){
        this.trainingProgramService.removePlayingCategory(id);
        return "redirect:/a/trainingPrograms";
    }

    @RequestMapping("/trainingProgram/edit/{id}")
    public String editPlayingCategory(@PathVariable("id") int id, Model model){
        model.addAttribute("trainingProgram", this.trainingProgramService.getPlayingCategoryById(id));
        model.addAttribute("trainingPrograms", this.trainingProgramService.listPlayingCategory());
        return "trainingProgram/createTrainingProgram";
    }

}
