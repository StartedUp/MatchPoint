package com.matchpoint.service;

import com.matchpoint.model.Role;

/**
 * Created by Prithu on 11/8/17.
 */
public interface RoleManager {
    public Role findByName(String name);
}
