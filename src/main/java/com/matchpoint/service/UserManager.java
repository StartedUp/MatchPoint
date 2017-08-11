package com.matchpoint.service;

import com.matchpoint.model.User;
import com.matchpoint.model.UserQuery;

import java.util.List;

/**
 * Created by gokul on 22/6/17.
 */

public interface UserManager {
    public User findByEmail(String email);
    public User save(User user);
    public List<User> findAll();
    public String updatePassword(String email,String password);
    /*public String grantMemberAccess(String email);*/
    public String grantAdminAccess(String email);
    public UserQuery save(UserQuery userQuery);
}
