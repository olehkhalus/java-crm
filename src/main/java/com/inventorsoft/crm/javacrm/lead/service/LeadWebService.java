package com.inventorsoft.crm.javacrm.lead.service;

import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import com.inventorsoft.crm.javacrm.domain.service.LeadService;
import com.inventorsoft.crm.javacrm.lead.dto.CreateLeadDTO;
import com.inventorsoft.crm.javacrm.lead.dto.LeadDTO;
import com.inventorsoft.crm.javacrm.lead.dto.UpdateLeadDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeadWebService {

    LeadService leadService;

    @Transactional(readOnly = true)
    public List<LeadDTO> getAll() {
        return leadService.getAll()
                .stream()
                .map(LeadDTO::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public boolean create(CreateLeadDTO createLeadDTO) {
        boolean status;
        if (leadService.findByEmail(createLeadDTO.getEmail().toLowerCase()).isPresent()) {
            status = false;
        } else {
            Lead lead = new Lead();
            lead.setFirstName(createLeadDTO.getFirstName());
            lead.setLastName(createLeadDTO.getLastName());
            lead.setEmail(createLeadDTO.getEmail().toLowerCase());
            lead.setCompanyName(createLeadDTO.getCompanyName());
            lead.setCompanySize(createLeadDTO.getCompanySize());
            lead.setWebsite(createLeadDTO.getWebsite());
            lead.setStatus(createLeadDTO.getStatus());
            lead.setCountry(createLeadDTO.getCountry());
            lead.setIndustry(createLeadDTO.getIndustry());
            lead.setNote(createLeadDTO.getNote());
            leadService.save(lead);
            status = true;
        }
        return status;
    }

    @Transactional(readOnly = true)
    public LeadDTO get(Long id) {
        return LeadDTO.of(leadService.get(id));
    }

    @Transactional
    public void delete(Long id) {
        leadService.delete(id);
    }

    @Transactional
    public boolean update(Long id, UpdateLeadDTO updateLeadDTO) {
        boolean status;
        Lead leadToBeUpdated = leadService.get(id);
        boolean emailExistsForOtherLead = leadService.findByEmail(updateLeadDTO.getEmail().toLowerCase())
                .map(Lead::getId)
                .stream()
                .anyMatch(Predicate.not(leadToBeUpdated.getId()::equals));
        if (emailExistsForOtherLead) {
            status = false;
        } else {
            leadToBeUpdated.setFirstName(updateLeadDTO.getFirstName());
            leadToBeUpdated.setLastName(updateLeadDTO.getLastName());
            leadToBeUpdated.setEmail(updateLeadDTO.getEmail().toLowerCase());
            leadToBeUpdated.setCompanyName(updateLeadDTO.getCompanyName());
            leadToBeUpdated.setCompanySize(updateLeadDTO.getCompanySize());
            leadToBeUpdated.setWebsite(updateLeadDTO.getWebsite());
            leadToBeUpdated.setStatus(updateLeadDTO.getStatus());
            leadToBeUpdated.setIndustry(updateLeadDTO.getIndustry());
            leadToBeUpdated.setCountry(updateLeadDTO.getCountry());
            leadToBeUpdated.setNote(updateLeadDTO.getNote());
            leadService.save(leadToBeUpdated);
            status = true;
        }
        return status;
    }
}
