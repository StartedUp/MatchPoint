package com.matchpoint.service;

import com.matchpoint.model.Role;
import com.matchpoint.model.User;
import com.matchpoint.model.UserQuery;
import com.matchpoint.repository.RoleRepository;
import com.matchpoint.repository.UserQueryRepository;
import com.matchpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gokul on 22/6/17.
 */
@Service
@Transactional
public class UserManagerImpl implements UserManager {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserQueryRepository userQueryRepository;
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
       try {
           userRepository.save(user);
           return "success";
       }catch (Exception e){
           e.printStackTrace();
           return "exceptionError";
       }
    }
    @Override
    public void manageAdminAccess(String email, String action) {
        User user=userRepository.findByEmail(email);
        Role role=roleRepository.findByName("admin");
        Set<Role> roles =user.getRoles();
        if (action.equals("grantAction"))
            roles.add(role);
        if (action.equals("revokeAction"))
            roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
    @Override
    public UserQuery save(UserQuery userQuery) {
        return userQueryRepository.save(userQuery);
    }

    @Override
    public User findOne(Integer id) {
        return userRepository.findOne(id);
    }
}
