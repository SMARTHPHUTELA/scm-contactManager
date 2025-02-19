package com.scm.contactmanger.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.helper.Helper;
import com.scm.contactmanger.services.UserService;

@ControllerAdvice
public class RootController {

    Logger logger=org.slf4j.LoggerFactory.getLogger(RootController.class);
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedinUserInformation(Model model, Authentication authentication){
        System.out.println("It will work for every call");
        if(authentication==null){
            return;
        }
        String username=Helper.getEmailOfLoggedUser(authentication);
        logger.info("USERNAME : "+username);
        user user1 = userService.getUserByEmail(username);
        System.out.println(user1.getUser_name());
        System.out.println(user1.getEmail());
        model.addAttribute("loggedInUser", user1);


    }

}
