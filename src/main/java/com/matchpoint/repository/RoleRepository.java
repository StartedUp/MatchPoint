package com.matchpoint.repository;

import com.matchpoint.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Prithu on 11/8/17.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
}
