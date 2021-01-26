package com.inventorsoft.crm.javacrm.user.profile.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class PasswordDto {

    @NotBlank
    @Size(min = 8, max = 255)
    String password;
}
