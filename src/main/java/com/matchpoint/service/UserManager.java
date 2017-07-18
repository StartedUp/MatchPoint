package com.matchpoint.service;

import com.matchpoint.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gokul on 22/6/17.
 */

public interface UserManager {
    public User findByEmail(String email);
    public User save(User user);
    public List<User> findAll();
    public String updatePassword(String email,String password);
}
