package com.scm.contactmanger.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.contactmanger.entity.Contact;
import com.scm.contactmanger.entity.user;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {
    Page<Contact> findByUsr(user usr,Pageable pageable);
    @Query("SELECT c FROM Contact c WHERE c.usr.id = :userid")
    List<Contact> findByUserId( @Param("userid") String userid);

    Page<Contact> findByUsrAndNameContaining(user usr,String nameKeyword,Pageable pageable);
    Page<Contact> findByUsrAndEmailContaining(user usr,String emailKeyword,Pageable pageable);
    Page<Contact> findByUsrAndPhonenumberContaining(user usr,String phoneKeyword,Pageable pageable);


    
}
