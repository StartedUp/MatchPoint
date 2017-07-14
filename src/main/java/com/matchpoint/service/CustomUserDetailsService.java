package com.matchpoint.service;

import com.matchpoint.model.LoggedinUser;
import com.matchpoint.model.User;
import com.matchpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by gokul on 22/6/17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            System.out.println("user email :" + email);
            User user = userRepository.findByEmail(email);
           /* System.out.println("user " + user.getEmail());
            System.out.println("user " + user.getPassword());
            System.out.println("user " + user.getRoles());*/
            if (user == null) {
                System.out.println("user " + email + " not found");
            }
            return new LoggedinUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("user Email not found");
        }
    }
}
