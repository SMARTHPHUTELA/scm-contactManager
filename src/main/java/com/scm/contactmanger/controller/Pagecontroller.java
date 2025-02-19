package com.scm.contactmanger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.forms.UserForm;
import com.scm.contactmanger.helper.message;
import com.scm.contactmanger.helper.messageType;
import com.scm.contactmanger.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import ch.qos.logback.core.model.Model;
// import org.springframework.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
@Controller
public class Pagecontroller {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String homepage(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home page HANDLER");
        model.addAttribute("name", "smarth");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        System.out.println("About page");
        return "about";
    }
    
    @GetMapping("/services")
    public String Service(Model model){
        System.out.println("Service page");
        return "services";
    }
    @GetMapping("/contact")
    public String contact(Model model){
        System.out.println("Contact Page");
        return "contact.html";
    }
    @GetMapping("/loginnow")
    public String Login(Model model){
        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);
        System.out.println("Get started ");
        return "loginnow";
    }
    @GetMapping("/getin")
    public String getin(){
        System.out.println("its login button");
        return "getin";
    }
    // @RequestMapping(value = "/do-register" ,method = RequestMethod.POST)
    @PostMapping("/do-register")
    public String processRegister( @Valid@ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        // user usr = user.builder()
        // .username(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .phonenumber(userForm.getPhonenumber())
        // .about(userForm.getAbout())
        // .profilepic("https://imgv3.fotor.com/images/blog-cover-image/10-profile-picture-ideas-to-make-you-stand-out.jpgl")
        // .build();
        if(rBindingResult.hasErrors()){
            message msg=message.builder().content("Enter Correct Info").type(messageType.red).build();
            session.setAttribute("message", msg);
            return "loginnow";
        }
        user usr=new user();
        usr.setUser_name(userForm.getName());
        usr.setAbout(userForm.getAbout());
        usr.setPhonenumber(userForm.getPhonenumber());
        usr.setEmail(userForm.getEmail()); 
        usr.setPassword(userForm.getPassword());
        usr.setProfilepic("https://imgv3.fotor.com/images/blog-cover-image/10-profile-picture-ideas-to-make-you-stand-out.jpgl");
        userService.saveUser(usr);
        message msg = message.builder().content("Registration Successfull").type(messageType.green).build();
        session.setAttribute("message", msg);
        return "redirect:/loginnow";

    }
   

}
