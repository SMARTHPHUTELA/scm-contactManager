package com.scm.contactmanger.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min=3,message = "Min 3 characters is Required")
    private String name;
    @Email(message = "Invalid Email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6 , message = "Minimum 6 characters required")
    private String password;
    @Size(min=8,max=14,message = "Invalid Phone number")
    private String phonenumber;
    @NotBlank(message = "About is required")
    private String about;

}
