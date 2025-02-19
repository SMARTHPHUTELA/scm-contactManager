package com.scm.contactmanger.controller;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.contactmanger.entity.Contact;
import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.forms.ContactForm;
import com.scm.contactmanger.forms.ContactSearchForm;
import com.scm.contactmanger.helper.Helper;
import com.scm.contactmanger.helper.constants;
import com.scm.contactmanger.helper.message;
import com.scm.contactmanger.helper.messageType;
import com.scm.contactmanger.services.ContactService;
import com.scm.contactmanger.services.ImageService;
import com.scm.contactmanger.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contact")
public class ContactController {
    private Logger logger=org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;    

    @Autowired
    private ImageService imageService;

    @GetMapping("/add-contact")
    public String add_contact(Model model){
        System.out.println("ADD contacts page");
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }

    @PostMapping("/process-contact")
    public String ProcessContact(@Valid@ModelAttribute ContactForm contactForm,BindingResult rBindingResult,Authentication authentication,HttpSession httpSession){
        if(rBindingResult.hasErrors()){
            message msg=message.builder().content("Enter correct info").type(messageType.red).build();
            httpSession.setAttribute("message", msg);
            return "user/add_contact";
        }
        String filename=UUID.randomUUID().toString();
        String url=imageService.uploadImage(contactForm.getContactimage(),filename);

        logger.info("File information : {}",contactForm.getContactimage().getOriginalFilename());
        message msg = message.builder().content("Your contact has been added").type(messageType.green).build();
        httpSession.setAttribute("message", msg);
        String username=Helper.getEmailOfLoggedUser(authentication);
        user usr=userService.getUserByEmail(username);
        Contact cnt=new Contact();
        cnt.setName(contactForm.getName());
        cnt.setEmail(contactForm.getEmail());
        cnt.setPhonenumber(contactForm.getPhonenumber());
        cnt.setAddress(contactForm.getAddress());
        cnt.setDescription(contactForm.getDescription());
        cnt.setFavorite(contactForm.isFavorite());
        cnt.setUsr(usr);
        cnt.setWebsiteLink(contactForm.getWebsite_link());
        cnt.setLinkedInLink(contactForm.getLinkedin_link());
        cnt.setPicture(url);
        cnt.setContactimage_publicid(filename);
        contactService.save(cnt);
        
        return "redirect:/user/contact/add-contact";
    }
    @GetMapping("/view-all")
    public String viewContacts(
        @RequestParam(value = "page",defaultValue = "0") int page,
        @RequestParam(value = "size",defaultValue = constants.Page_size+"") int size,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value = "direction",defaultValue = "asc") String direction,
        Model model,Authentication authentication){
        

        ContactSearchForm contactSearchForm=new ContactSearchForm();
        model.addAttribute("contactSearchForm", contactSearchForm);

        String username=Helper.getEmailOfLoggedUser(authentication);
        user usr = userService.getUserByEmail(username);
        Page<Contact> PageContact = contactService.getByUser(usr,page,size,sortBy,direction);
        model.addAttribute("PageContact", PageContact);
        model.addAttribute("PageSize", constants.Page_size);
        return "user/contacts";
    }


    @GetMapping("/search")
    public String searchContacts(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "page",defaultValue="0") int page,
        @RequestParam(value = "size",defaultValue = constants.Page_size+"") int size,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value = "order",defaultValue = "asc") String order,
        Model model,
        Authentication authentication){
            String username=Helper.getEmailOfLoggedUser(authentication);
            user usr = userService.getUserByEmail(username);
            Page<Contact> PageContact=null;
            if(contactSearchForm.getField().equalsIgnoreCase("name")){
                PageContact = contactService.searchByName(usr,contactSearchForm.getValue(), page, size, sortBy, order);
            }
            else if(contactSearchForm.getField().equalsIgnoreCase("email")){
                PageContact=contactService.searchByEmail(usr, contactSearchForm.getValue(), page, size, sortBy, order);
            }
            else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
                PageContact=contactService.searchByPhoneNumber(usr, contactSearchForm.getValue(), page, size, sortBy, order);
            }
            model.addAttribute("PageContact", PageContact);
            model.addAttribute("contactSearchForm", contactSearchForm);
            model.addAttribute("PageSize", constants.Page_size);
            return "user/search";

    }
    @GetMapping("/delete/{contactId}")
    public String DeleteContact(@PathVariable String contactId){
        contactService.deleteById(contactId);
        return "redirect:/user/contact/view-all";
    }

    @GetMapping("/view-previous/{contactId}")
    public String goToUpdateContactPage(@PathVariable String contactId,Model model){
        Contact cnt = contactService.getById(contactId);
        ContactForm contactForm=new ContactForm();
        contactForm.setName(cnt.getName());
        contactForm.setEmail(cnt.getEmail());
        contactForm.setPhonenumber(cnt.getPhonenumber());
        contactForm.setAddress(cnt.getAddress());
        contactForm.setDescription(cnt.getDescription());
        contactForm.setWebsite_link(cnt.getWebsiteLink());
        contactForm.setLinkedin_link(cnt.getLinkedInLink());
        contactForm.setFavorite(cnt.isFavorite());
        contactForm.setPicture(cnt.getPicture());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_page";
    }

    @PostMapping("/update/{contactId}")
    public String UpdateContact(@PathVariable String contactId,@Valid@ModelAttribute ContactForm contactForm,BindingResult rBindingResult, Model model,HttpSession httpSession){
        Contact con = contactService.getById(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhonenumber(contactForm.getPhonenumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setWebsiteLink(contactForm.getWebsite_link());
        con.setLinkedInLink(contactForm.getLinkedin_link());
        con.setFavorite(contactForm.isFavorite());
        
        // process (update image)
        if(contactForm.getContactimage()!=null && !contactForm.getContactimage().isEmpty()){
            String filename=UUID.randomUUID().toString();
            String updateImage = imageService.uploadImage(contactForm.getContactimage(), filename);
            con.setPicture(updateImage);
            contactForm.setPicture(updateImage);
            con.setContactimage_publicid(filename);
        }
        if(rBindingResult.hasErrors()){
            message msg = message.builder().content("Enter correct Info").type(messageType.red).build();
            httpSession.setAttribute("message", msg);
            return"user/update_contact_page.html";
        }
       
         contactService.update(con);
        return "redirect:/user/contact/view-all";
    }

    
    

    
}
