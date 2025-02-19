package com.scm.contactmanger.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.contactmanger.entity.Contact;
import com.scm.contactmanger.entity.user;


public interface ContactService {
    Contact save(Contact cnt);
    Contact getById(String id);
    List<Contact> getAllContacts();
    void deleteById(String id);
    Contact update(Contact cnt);
    Page<Contact> getByUser(user usr,int page ,int size,String sortField, String sortDirection);
    List<Contact> getByUserId(String userid);
    List<Contact> search(String name,String email, String phonenumber);

    Page<Contact> searchByName(user usr,String nameKeyword,int page,int size,String sortBy,String order);
    Page<Contact> searchByEmail(user usr,String emailKeyword,int page,int size,String sortBy,String order);
    Page<Contact> searchByPhoneNumber(user usr,String phoneKeyword,int page,int size,String sortBy,String order);
    


    
}
