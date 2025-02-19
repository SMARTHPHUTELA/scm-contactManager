package com.scm.contactmanger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user implements UserDetails {
    @Id
    @Column(name = "user_ide")
    private String id;
    @Column(unique = true,nullable = false)
    private String user_name;
    private String password;
    private String email;
    @Column(length = 1000)
    private String about;
    private String profilepic;
    private String phonenumber;
    @Builder.Default
    private boolean enabled=true;
    @Builder.Default
    private boolean email_verified=false;
    @Builder.Default
    private boolean phone_verified=false;
    // @Enumerated(value = EnumType.STRING)
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private providers provider=providers.SELF;
    private String providerUserId;
    @OneToMany(mappedBy = "usr",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @Builder.Default
    private List<Contact> contacts=new ArrayList<>();
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles=roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }
    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword(){
        return this.password;
    }

}
