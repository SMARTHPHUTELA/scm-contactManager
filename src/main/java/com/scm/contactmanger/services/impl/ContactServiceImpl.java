package com.scm.contactmanger.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.contactmanger.entity.Contact;
import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.helper.ResourceNotFoundException;
import com.scm.contactmanger.repositories.ContactRepo;
import com.scm.contactmanger.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService  {
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact cnt) {
       cnt.setId(UUID.randomUUID().toString());
       return contactRepo.save(cnt);
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElse(null);
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> all_contacts=contactRepo.findAll();
        return all_contacts;
    }

    @Override
    public void deleteById(String id) {
       contactRepo.deleteById(id);
    }

    @Override
    public Contact update(Contact cnt) {
       Contact cnt_old=contactRepo.findById(cnt.getId()).orElseThrow(()-> new ResourceNotFoundException("This Contact doesnot exist"));
       cnt_old.setName(cnt.getName());
       cnt_old.setEmail(cnt.getEmail());
       cnt_old.setPhonenumber(cnt.getPhonenumber());
       cnt_old.setAddress(cnt.getAddress());
       cnt_old.setDescription(cnt.getDescription());
       cnt_old.setFavorite(cnt.isFavorite());
       cnt_old.setWebsiteLink(cnt.getWebsiteLink());
       cnt_old.setLinkedInLink(cnt.getLinkedInLink());
       cnt_old.setContactimage_publicid(cnt.getContactimage_publicid());
       cnt_old.setPicture(cnt.getPicture());
    //    now cnt_old(contact) gets updated with all the details of cnt(conatct) passsed as parameter
       Contact saved = contactRepo.save(cnt_old);
       return saved;
    }

    @Override
    public Page<Contact> getByUser(user usr,int page,int size,String sortBy ,String direction) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page,size,sort);
        return contactRepo.findByUsr(usr,pageable);
    }

    @Override
    public List<Contact> getByUserId(String userid) {
        return contactRepo.findByUserId(userid);
        

    }

    @Override
    public List<Contact> search(String name, String email, String phonenumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public Page<Contact> searchByName(user usr, String nameKeyword, int page, int size, String sortBy, String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUsrAndNameContaining(usr, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(user usr, String emailKeyword, int page, int size, String sortBy, String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUsrAndEmailContaining(usr, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(user usr, String phoneKeyword, int page, int size, String sortBy,String order) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(page, size,sort);
        return contactRepo.findByUsrAndPhonenumberContaining(usr, phoneKeyword, pageable);
    }

   

}
