package com.matchpoint.service;

import com.matchpoint.model.User;
import com.matchpoint.model.UserQuery;

import java.util.List;

/**
 * Created by gokul on 22/6/17.
 */

public interface UserManager {
    public User findByEmail(String email);
    User findOne(Integer id);
    public User save(User user);
    public List<User> findAll();
    public String updatePassword(String email,String password);
    public UserQuery save(UserQuery userQuery);
    public void manageAdminAccess(String email,String action);
}
