package com.matchpoint.service;

import com.matchpoint.model.Role;
import com.matchpoint.model.User;
import com.matchpoint.model.User_Role;
import com.matchpoint.repository.UserRepository;
import com.matchpoint.repository.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
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
    User_RoleRepository user_roleRepository;
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
    public String grantAdminAccess(String email) {
        //Query to grant Admin access. Role id -> 1
        //INSERT INTO `matchpoint`.`user_role` (`users_id`, `roles_id`) VALUES ('8', '1');
        System.out.println("Receied Email is "+email);
        User_Role admin_role=new User_Role();
        User user=userRepository.findByEmail(email);
        System.out.println(user.toString());
        admin_role.setUsers_id(user.getId());
        admin_role.setRoles_id(1);
        user_roleRepository.save(admin_role);
        return "listUsers";
    }

    @Override
    public String grantMemberAccess(String email) {
        //Query to grant Member access. Role id -> 2
        //INSERT INTO `matchpoint`.`user_role` (`users_id`, `roles_id`) VALUES ('8', '2');
        User_Role user_role=new User_Role();
        User user=userRepository.findByEmail(email);
        System.out.println(user.toString());
        user_role.setUsers_id(user.getId());
        user_role.setRoles_id(2);
        user_roleRepository.save(user_role);
        System.out.println("In JPA REPOSITORY"+user.toString());
        return "listUsers";
    }
}
