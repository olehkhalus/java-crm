package com.inventorsoft.crm.javacrm.lead.dto;

import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLeadDTO {

    @NotNull
    @Positive
    Long id;

    @NotBlank
    @Size(max = 50)
    String firstName;

    @NotBlank
    @Size(max = 50)
    String lastName;

    @NotBlank
    @Email(message = "Email is not valid")
    @Size(max = 50)
    String email;

    @NotBlank
    @Size(max = 50)
    String companyName;

    @PositiveOrZero
    int companySize;

    @Size(max = 50)
    String website;

    LeadStatus status;

    @NotBlank
    @Size(max = 50)
    String country;

    @NotBlank
    @Size(max = 50)
    String industry;

    @Size(max = 150)
    String note;

}
