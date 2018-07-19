package com.matchpoint.Util;

import com.matchpoint.model.User;
import com.matchpoint.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by root on 18/7/18.
 */
@Service
public class SessionUtil {

    @Autowired
    private UserManager userManager;

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication !=null && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public User getCurrentuser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userManager.findByEmail(user.getEmail());
        return currentUser;
    }
}
