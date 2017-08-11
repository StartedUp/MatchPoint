package com.matchpoint.repository;

import com.matchpoint.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gokul on 19/7/17.
 */
public interface UserRoleRepository extends JpaRepository <UserRole, Integer> {
}
