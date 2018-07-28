package com.matchpoint.controllers.admin;

import com.matchpoint.model.Fee;
import com.matchpoint.service.FeeService;
import com.matchpoint.service.PlayerCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Balaji on 27/7/18.
 */
@Controller
public class FeeController extends AdminRootController{
    private static final Logger LOGGER = LoggerFactory.getLogger(FeeController.class);

    @Autowired
    private FeeService feeService;
    @Autowired
    private PlayerCategoryService playerCategoryService;

    @RequestMapping(value = "/fee-list", method = RequestMethod.GET)
    public String listFee(Model model) {
        model.addAttribute("feeList", this.feeService.listFee());
        return "fee/fee-list";
    }

    @RequestMapping("/fee/showCreatePage")
    public  String showCreatePage(Model model) {
        model.addAttribute("fee", new Fee());
        model.addAttribute("playerCategories", playerCategoryService.listplayerCategory());
        return "fee/createFee";
    }

    //For add and update fee both
    @RequestMapping(value= "/fee/add", method = RequestMethod.POST)
    public String addFee(@ModelAttribute("fee") Fee fee, Model model){
        LOGGER.info("fee : {}",fee);
        if(fee.getId() == 0){
            //new fee, add it
            this.feeService.addFee(fee);
            model.addAttribute("createSuccess", true);
        }else{
            //existing fee, call update
            model.addAttribute("editSuccess", true);
            this.feeService.updateFee(fee);
        }
        return "redirect:/a/fee-list";

    }

    @RequestMapping("/fee/remove/{id}")
    public String removefee(@PathVariable("id") int id){
        this.feeService.removeFee(id);
        return "redirect:/a/fee-list";
    }

    @RequestMapping("/fee/edit/{id}")
    public String editfee(@PathVariable("id") int id, Model model){
        model.addAttribute("fee", this.feeService.getFeeById(id));
        model.addAttribute("playerCategories", playerCategoryService.listplayerCategory());
        return "fee/createFee";
    }
}
