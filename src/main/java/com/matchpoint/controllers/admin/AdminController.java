package com.matchpoint.controllers.admin;

import com.matchpoint.enums.EventTypesEnum;
import com.matchpoint.model.Event;
import com.matchpoint.model.EventRegistration;
import com.matchpoint.model.Role;
import com.matchpoint.model.User;
import com.matchpoint.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokul on 10/7/17.
 */
@Controller
public class AdminController extends AdminRootController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class.getName());
    private String LOCAL_UPLOAD_PATH="/home/gokul/Git_projects/MatchPoint/src/main/resources/static/img/gallery/";
    @Autowired
    private UserManager userManager;
    @Autowired
    private EventManager eventManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private EventRegistrationManager eventRegistrationManager;
    @Autowired
    private ResourcePatternResolver resourcePatternResolver;
    @Autowired
    private PlayingCategoryService playingCategoryService;
    @Autowired
    private MailService mailService;

    /*@PreAuthorize("hasAnyRole('admin')")*/
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showAdminPage(){
        return "adminHome";
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public String showUsers(Model model){
        List<User> users;
        users = userManager.findAll();
        Role role=roleManager.findByName("admin");
        model.addAttribute("users", users);
        model.addAttribute("roleAdmin",role);
        return "listUsers";
    }
    @GetMapping("/createEvent")
    public String createEvent(Model model){
        model.addAttribute("playingCategories", playingCategoryService.listPlayingCategory())
                .addAttribute("eventTypes", EventTypesEnum.values());
        return "createEvent";
    }

    @RequestMapping(value = "/registerEvent", method = RequestMethod.POST)
    public String register(@ModelAttribute("event")Event event, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "createEvent";
        }
        event=eventManager.save(event);
        mailService.sendEventNotification(event);
        model.addAttribute("eventRegisterSuccess",true);
        return "redirect:/a/listEvents";
    }
    @RequestMapping(value = "/listEvents", method = RequestMethod.GET)
    public String showEvents(Model model){
        List<Event> events; List<EventRegistration> eventRegistrations;List<String> usersCount;
        events = eventManager.findAllByOrderByEndDateDesc();
        usersCount = eventRegistrationManager.registrationCount(events);
        model.addAttribute("events", events);
        model.addAttribute("registrationCount",usersCount);
        return "listEventsAsAdmin";
    }
    @GetMapping("/eventRegistrationList/{eventId}")
    public String showRegisteredUsersList(Model model, @PathVariable("eventId") Integer eventId){
        List<EventRegistration> eventRegistrations;
        Event event=eventManager.findById(eventId);
        eventRegistrations=eventRegistrationManager.findByEvent_id(eventId);
        model.addAttribute("eventRegistrations",eventRegistrations);
        model.addAttribute("event", event);
        return "registeredUsers";
    }
    @RequestMapping("/manageRoles")
    public String adminAccess(@RequestParam("userEmail") String email,
                              @RequestParam("action") String action,
                              Model model){
            userManager.manageAdminAccess(email, action);
           /* model.addAttribute("RolesModified",action+" Done");*/
            return "redirect:/a/listUsers";
    }
    @RequestMapping("/uploadAlbum")
    public String showUploadAlbumpage(){return "uploadAlbum";
    }
    @PostMapping("/uploadAlbumToGallery")
    public String uploadAlbumToGallery(@RequestParam("albumName") String albumName,
                                        @RequestParam("image")MultipartFile[] files,
                                        RedirectAttributes redirectAttributes)
    {
        //Creating a new directory with Album Name
        new File(LOCAL_UPLOAD_PATH + albumName).mkdir();
        //Save the uploaded file to this folder
        String UPLOADED_FOLDER = LOCAL_UPLOAD_PATH+albumName+"/";
        for (MultipartFile file:files){
        if (files.length==0){
            redirectAttributes.
                    addFlashAttribute("message","please upload a picture and submit");
        }
        try {
            byte[] bytes=file.getBytes();
            Path path=Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path,bytes);
        }catch (Exception e){
            e.printStackTrace();
        }}
        redirectAttributes.
                addFlashAttribute("message","   You have successfully uploaded "+files.length+" files");
        return "redirect:/a/uploadAlbum";
    }
    @RequestMapping("/photoGallery")
    public String showphotoGalleryHome(Model model){
        try {
            File file = new File(LOCAL_UPLOAD_PATH);
            String[] names = file.list();
            List albumNames =new ArrayList();
            for(String name : names) {
                if (new File(LOCAL_UPLOAD_PATH+ name).isDirectory())
                    albumNames.add(name);
              }
            model.addAttribute("albumNames",albumNames);
            } catch (Exception e){
            e.printStackTrace();
        }
        return "photoGalleryHome";
    }
    @RequestMapping("/showAlbum/{albumName}")
    public String showAlbum(Model model, @PathVariable("albumName") String albumName){
        String[] imageFiles=null;
        try {
            File file = new File(LOCAL_UPLOAD_PATH + albumName+"/");
             imageFiles = file.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("path","/img/gallery/");
        model.addAttribute("heading",albumName);
        model.addAttribute("albumName",albumName);
        model.addAttribute("imageFiles",imageFiles);
        return "photoGallery";
    }
    @RequestMapping("/manageUser")
    public String manageUser(@RequestParam("block") boolean block, @RequestParam("email") String email){
        LOGGER.info("Managing users {}",block?"blocking":"unblocking");
        User user=userManager.findByEmail(email);
        user.setActive(!block);
        userManager.save(user);
        return "redirect:/a/listUsers";
    }
}
