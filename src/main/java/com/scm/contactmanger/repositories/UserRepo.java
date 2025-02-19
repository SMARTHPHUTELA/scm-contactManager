package com.scm.contactmanger.repositories;

import java.util.Optional;

// import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.contactmanger.entity.user;

@Repository
public interface UserRepo extends JpaRepository<user,String> { 
    public Optional<user> findUserByEmail(String email);

}
