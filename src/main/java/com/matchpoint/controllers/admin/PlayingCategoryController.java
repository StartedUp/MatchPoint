package com.matchpoint.controllers.admin;

import com.matchpoint.model.PlayingCategory;
import com.matchpoint.service.PlayingCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by root on 30/6/18.
 */
public class PlayingCategoryController extends AdminController{

    @Autowired
    private PlayingCategoryService playingCategoryService;
    

    @RequestMapping(value = "/playingCategories", method = RequestMethod.GET)
    public String listPlayingCategory(Model model) {
        model.addAttribute("listPlayingCategory", this.playingCategoryService.listPlayingCategory());
        return "playingCategories";
    }

    @RequestMapping("/playingCategory/showCreatePage")
    public  String showCreatePage(Model model) {
        model.addAttribute("playingCategory", new PlayingCategory());
        return "playingCategory-createPage";
    }

    //For add and update PlayingCategory both
    @RequestMapping(value= "/playingCategory/add", method = RequestMethod.POST)
    public String addPlayingCategory(@ModelAttribute("PlayingCategory") PlayingCategory playingCategory){

        if(playingCategory.getId() == 0){
            //new PlayingCategory, add it
            this.playingCategoryService.addPlayingCategory(playingCategory);
        }else{
            //existing PlayingCategory, call update
            this.playingCategoryService.updatePlayingCategory(playingCategory);
        }

        return "redirect:/playingCategories";

    }

    @RequestMapping("/playingCategory/remove/{id}")
    public String removePlayingCategory(@PathVariable("id") int id){

        this.playingCategoryService.removePlayingCategory(id);
        return "redirect:/playingCategory";
    }

    @RequestMapping("/playingCategory/edit/{id}")
    public String editPlayingCategory(@PathVariable("id") int id, Model model){
        model.addAttribute("playingCategory", this.playingCategoryService.getPlayingCategoryById(id));
        model.addAttribute("listPlayingCategory", this.playingCategoryService.listPlayingCategory());
        return "PlayingCategory";
    }
}
