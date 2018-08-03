package com.matchpoint.controllers.admin;

import com.matchpoint.model.User;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by root on 2/8/18.
 */
@RestController
public class AdminRestController extends AdminRootController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/member-details-approval", method = RequestMethod.POST)
    public ResponseEntity memberDetailsApproval(@RequestParam Integer id) {
        try {
            User user = userManager.findOne(id);
            user.setAdminApproved(!user.isAdminApproved());
            User save = userManager.save(user);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(false);
    }
}
