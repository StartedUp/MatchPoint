package com.matchpoint.service;

import com.matchpoint.model.User;
import com.matchpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;

/**
 * Created by gokul on 22/6/17.
 */
@Service
@Transactional
public class UserManagerImpl implements UserManager {

    @Autowired
    UserRepository userRepository;
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public String updatePassword(String email,String password) {
        User user=userRepository.findByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        System.out.println(user.toString());
        return "success";
    }
}
