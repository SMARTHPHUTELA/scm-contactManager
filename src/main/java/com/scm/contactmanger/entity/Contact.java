
package com.scm.contactmanger.entity;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name="contacts")
public class Contact {
    @Id
    private String id;
    private String name;
    private String phonenumber;
    private String email;
    @Column(length=1000)
    private String address;
    private String description;
    private String picture;
    private String websiteLink;
    private String linkedInLink;
    private String contactimage_publicid;
    @Builder.Default
    private boolean favorite=false;
    @Builder.Default
    private List<String> social_link=new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private user usr;
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @Builder.Default
    private List<SocialLink> links=new ArrayList<>();
}
