package com.scm.contactmanger.forms;



import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "Contact Name is required")
    @Size(min=3 ,message = "Minimum 3 Characters Required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email address [example@gmail.com]")
    private String email;
    @NotBlank
    // @Size(min=8,max=14,message = "Invalid Phone number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phonenumber;

    @NotBlank(message = "Address is Required")
    private String address;
    private String description;
    private boolean favorite;
    private String website_link;
    private String linkedin_link;
    private MultipartFile contactimage;
    private String picture;

}
