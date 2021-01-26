package com.inventorsoft.crm.javacrm.user.registration.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserDto {

    @NotBlank
    @Email(message = "Email is not valid")
    @Size(max = 60)
    String email;

    @NotBlank
    @Size(min = 8, max = 255)
    String password;

    @NotBlank
    @Size(max = 20)
    String firstName;

    @NotBlank
    @Size(max = 20)
    String lastName;

    @NotBlank
    @Size(max = 200)
    String companyName;

}