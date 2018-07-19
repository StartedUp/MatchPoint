package com.matchpoint.controllers.admin;

import com.matchpoint.enums.GenderTypeEnum;
import com.matchpoint.model.PlayingCategory;
import com.matchpoint.service.PlayingCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * Created by root on 30/6/18.
 */
@Controller
public class PlayingCategoryController extends AdminRootController{

    @Autowired
    private PlayingCategoryService playingCategoryService;

    @RequestMapping(value = "/playingCategories", method = RequestMethod.GET)
    public String listPlayingCategory(Model model) {
        model.addAttribute("playingCategories", this.playingCategoryService.listPlayingCategory());
        return "playingCategories";
    }

    @RequestMapping("/playingCategory/showCreatePage")
    public  String showCreatePage(Model model) {
        model.addAttribute("playingCategory", new PlayingCategory());
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        return "createPlayingCategory";
    }

    //For add and update PlayingCategory both
    @RequestMapping(value= "/playingCategory/add", method = RequestMethod.POST)
    public String addPlayingCategory(@ModelAttribute("PlayingCategory") PlayingCategory playingCategory, Model model){

        if(playingCategory.getId() == 0){
            //new PlayingCategory, add it
            this.playingCategoryService.addPlayingCategory(playingCategory);
            model.addAttribute("createSuccess", true);
        }else{
            //existing PlayingCategory, call update
            model.addAttribute("editSuccess", true);
            this.playingCategoryService.updatePlayingCategory(playingCategory);
        }

        return "redirect:/a/playingCategories";

    }

    @RequestMapping("/playingCategory/remove/{id}")
    public String removePlayingCategory(@PathVariable("id") int id){
        this.playingCategoryService.removePlayingCategory(id);
        return "redirect:/a/playingCategories";
    }

    @RequestMapping("/playingCategory/edit/{id}")
    public String editPlayingCategory(@PathVariable("id") int id, Model model){
        model.addAttribute("playingCategory", this.playingCategoryService.getPlayingCategoryById(id));
        model.addAttribute("playingCategories", this.playingCategoryService.listPlayingCategory());
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        return "createPlayingCategory";
    }
}
