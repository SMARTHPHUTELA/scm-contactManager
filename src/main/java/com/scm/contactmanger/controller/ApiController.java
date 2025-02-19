package com.scm.contactmanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.contactmanger.entity.Contact;
import com.scm.contactmanger.helper.ResourceNotFoundException;
import com.scm.contactmanger.repositories.ContactRepo;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactRepo contactRepo;
    @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        return contactRepo.findById(contactId).orElseThrow(()->new ResourceNotFoundException("This contact doesnot exist"));
    }

}
