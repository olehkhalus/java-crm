package com.inventorsoft.crm.javacrm.config;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.domain.service.LeadService;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(ApplicationConstant.SpringProfile.LOCAL)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitialization implements ApplicationListener<ApplicationReadyEvent> {

    UserService userService;
    LeadService leadService;

    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (leadService.findByEmail(ApplicationConstant.Initializer.LEAD_DEFAULT_EMAIL).isEmpty()) {
            createLead();
        }
        if (userService.findByEmail(ApplicationConstant.Initializer.ADMIN_DEFAULT_EMAIL).isEmpty()) {
            createUser();
        }
    }

    private void createLead() {
        Lead lead = new Lead();
        lead.setFirstName("Admin");
        lead.setLastName("Developer");
        lead.setCompanyName("InventorSoft");
        lead.setCompanySize(50);
        lead.setCountry("Ukraine");
        lead.setEmail(ApplicationConstant.Initializer.LEAD_DEFAULT_EMAIL);
        lead.setIndustry("Web Development");
        lead.setStatus(LeadStatus.IN_PROGRESS);
        lead.setNote("Studying in IT Academy");
        lead.setWebsite("javacrm.com");
        leadService.save(lead);
    }

    private void createUser() {
        User admin = new User();
        admin.setEmail(ApplicationConstant.Initializer.ADMIN_DEFAULT_EMAIL);
        admin.setFirstName("Admin");
        admin.setLastName("Adminow");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setCompanyName("Administration");
        userService.save(admin);
    }
}