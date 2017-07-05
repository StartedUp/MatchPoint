package com.matchpoint.repository;

import com.matchpoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by gokul on 22/6/17.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
