package com.matchpoint.controllers.admin;

import com.matchpoint.enums.GenderTypeEnum;
import com.matchpoint.model.PlayerCategory;
import com.matchpoint.service.PlayerCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

/**
 * Created by Prithu on 24/7/18.
 */
@Controller
public class PlayerCategoryController extends AdminRootController{
    @Autowired
    private PlayerCategoryService playerCategoryService;

    @RequestMapping(value = "/player-categories", method = RequestMethod.GET)
    public String listplayerCategory(Model model) {
        model.addAttribute("playerCategories", this.playerCategoryService.listplayerCategory());
        return "playerCategory/playerCategories";
    }

    @RequestMapping("/player-category/showCreatePage")
    public  String showCreatePage(Model model) {
        model.addAttribute("playerCategory", new PlayerCategory());
        return "playerCategory/createPlayerCategory";
    }

    //For add and update playerCategory both
    @RequestMapping(value= "/player-category/add", method = RequestMethod.POST)
    public String addplayerCategory(@ModelAttribute("playerCategory") PlayerCategory playerCategory, Model model){

        if(playerCategory.getId() == 0){
            //new playerCategory, add it
            this.playerCategoryService.addPlayerCategory(playerCategory);
            model.addAttribute("createSuccess", true);
        }else{
            //existing playerCategory, call update
            model.addAttribute("editSuccess", true);
            this.playerCategoryService.updatePlayerCategory(playerCategory);
        }
        return "redirect:/a/player-categories";

    }

    @RequestMapping("/player-category/remove/{id}")
    public String removeplayerCategory(@PathVariable("id") int id){
        this.playerCategoryService.removePlayerCategory(id);
        return "redirect:/a/player-categories";
    }

    @RequestMapping("/player-category/edit/{id}")
    public String editplayerCategory(@PathVariable("id") int id, Model model){
        model.addAttribute("playerCategory", this.playerCategoryService.getPlayerCategoryById(id));
        model.addAttribute("playerCategories", this.playerCategoryService.listplayerCategory());
        model.addAttribute("genderTypes", Arrays.asList(GenderTypeEnum.values()));
        return "playerCategory/updatePlayerCategory";
    }
}
