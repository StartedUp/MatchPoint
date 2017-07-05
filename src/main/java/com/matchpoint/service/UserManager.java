package com.matchpoint.service;

import com.matchpoint.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by gokul on 22/6/17.
 */
@Service
public interface UserManager {
    public User findByEmail(String email);
}
