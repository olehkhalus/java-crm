package com.inventorsoft.crm.javacrm.user.password.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPasswordChangeDto {

    @NotBlank
    @Email(message = "Email is not valid")
    @Size(max = 60)
    String email;

    @NotBlank
    @Size(min = 8, max = 255)
    String password;
}
