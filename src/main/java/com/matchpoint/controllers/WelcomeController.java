package com.matchpoint.controllers;

import com.matchpoint.model.Event;
import com.matchpoint.model.User;
import com.matchpoint.model.UserQuery;
import com.matchpoint.service.EventManager;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gokul on 16/7/17.
 */
@Controller
public class WelcomeController {
    private String LOCAL_AwardsAlbum_PATH="/home/gokul/Git_projects/MatchPoint/src/main/resources/static/img/gallery/";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;
    @Autowired
   private EventManager eventManager;
    @GetMapping("/events")
    public String showMemberHome(Model model){
        List<Event> events;
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        events = eventManager.findByEndDateAfter(cal.getTime());
        model.addAttribute("events", events);
        return "listEvents";
    }
    @RequestMapping("/userQuery")
    public String saveUserQuery(@ModelAttribute("userQuery")UserQuery userQuery, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            System.out.println(userQuery.toString());
            System.out.println(bindingResult);
            return "/#contact";
        }
        try {
            userManager.save(userQuery);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("QueryUpdateSuccess",true);
        return "redirect:/";
    }
    @RequestMapping("/showAwardsAlbum")
    public String showAlbum(Model model){
        String[] imageFileNames=null;
        List<String> imageFiles=new ArrayList<String>();
        try {
            File file = new File(LOCAL_AwardsAlbum_PATH);
            imageFileNames = file.list();
            for (String fileName:imageFileNames) {
                if(fileName.contains(".j") || fileName.contains(".p"))
                    imageFiles.add(fileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("path","/img/gallery/");
        model.addAttribute("heading","Awards and Achievments");
        model.addAttribute("albumName","");
        model.addAttribute("imageFiles",imageFiles);
        return "photoGallery";
    }

}
