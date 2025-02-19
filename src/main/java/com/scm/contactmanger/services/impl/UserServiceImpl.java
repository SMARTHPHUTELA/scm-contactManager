package com.scm.contactmanger.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.hibernate.validator.constraints.UUID;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.helper.ResourceNotFoundException;
import com.scm.contactmanger.helper.constants;
import com.scm.contactmanger.repositories.UserRepo;
import com.scm.contactmanger.services.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unused")
    private Logger logger=org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Override
    public void saveUser(user usr) {
        String userid=UUID.randomUUID().toString();
        usr.setId(userid);
        usr.setPassword(passwordEncoder.encode(usr.getPassword()));
        usr.setRoleList(List.of(constants.role_user));
        userRepo.save(usr);
       
    }

    @Override
    public Optional<user> getUserById(String id) {
       return userRepo.findById(id);
    }

    @Override
    public Optional<user> updateUser(user usr) {
        user usr2=userRepo.findById(usr.getId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        usr2.setUser_name(usr.getUser_name());
        usr2.setPassword(usr.getPassword());
        usr2.setEmail(usr.getEmail());
        usr2.setAbout(usr.getAbout());
        usr2.setPhonenumber(usr.getPhonenumber());
        usr2.setProfilepic(usr.getProfilepic());
        usr2.setEnabled(usr.isEnabled());
        usr2.setEmail_verified(usr.isEmail_verified());
        usr2.setPhone_verified(usr.isPhone_verified());
        usr2.setProvider(usr.getProvider());
        usr2.setProviderUserId(usr.getProviderUserId());

        user saved=userRepo.save(usr2);
        return Optional.ofNullable(saved);
    }

    @Override
    public void deleteUser(String id) {
       user usr2 = userRepo.findById(id).orElse(null);
       userRepo.delete(usr2);
    }

    @Override
    public boolean isUserExist(String userId) {
        user usrfound = userRepo.findById(userId).orElse(null);
        return usrfound!=null?true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        user userByEmail = userRepo.findUserByEmail(email).orElse(null);
        return userByEmail!=null?true:false;
    }

    @Override
    public List<user> getAllUsers() {
        List<user> allUsers = userRepo.findAll();
        return allUsers;
    }

    @Override
    public user getUserByEmail(String email) {
        return userRepo.findUserByEmail(email).orElse(null);
    }

}
