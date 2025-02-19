package com.scm.contactmanger.controller;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.contactmanger.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger=org.slf4j.LoggerFactory.getLogger(this.getClass());
    @GetMapping("/dashboard")
    public String userDashboard(){
        System.out.println("User Dashboard");
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication){
        String username=Helper.getEmailOfLoggedUser(authentication);
        logger.info("Logged in username: {}",username);

        return "user/profile";
    }
}

