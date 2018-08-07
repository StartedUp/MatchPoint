package com.matchpoint.repository;

import com.matchpoint.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gokul on 22/6/17.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findByActive(boolean active);
}
