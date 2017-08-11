package com.matchpoint.service;

import com.matchpoint.model.Role;
import com.matchpoint.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Prithu on 11/8/17.
 */
@Service
@Transactional
public class RoleManagerImpl implements RoleManager{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
