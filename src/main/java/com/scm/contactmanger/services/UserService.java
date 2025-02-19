package com.scm.contactmanger.services;



import java.util.Optional;

import java.util.*;

import com.scm.contactmanger.entity.user;


public interface UserService {

    void saveUser(user usr);
    Optional<user> getUserById(String id);
    Optional<user> updateUser(user usr);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<user> getAllUsers();
    user getUserByEmail(String email);
}
