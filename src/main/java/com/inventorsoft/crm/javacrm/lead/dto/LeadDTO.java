package com.inventorsoft.crm.javacrm.lead.dto;

import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeadDTO {

    Long id;
    String firstName;
    String lastName;
    String email;
    String companyName;
    int companySize;
    String website;
    LeadStatus status;
    String country;
    String industry;
    String note;
    LocalDate creatingDate;

    public static LeadDTO of(Lead lead) {
        LeadDTO leadDTO = new LeadDTO();
        leadDTO.setId(lead.getId());
        leadDTO.setFirstName(lead.getFirstName());
        leadDTO.setLastName(lead.getLastName());
        leadDTO.setEmail(lead.getEmail());
        leadDTO.setCompanyName(lead.getCompanyName());
        leadDTO.setCompanySize(lead.getCompanySize());
        leadDTO.setWebsite(lead.getWebsite());
        leadDTO.setStatus(lead.getStatus());
        leadDTO.setCountry(lead.getCountry());
        leadDTO.setIndustry(lead.getIndustry());
        leadDTO.setNote(lead.getNote());
        leadDTO.setCreatingDate(lead.getCreatingDate());
        return leadDTO;
    }
}
