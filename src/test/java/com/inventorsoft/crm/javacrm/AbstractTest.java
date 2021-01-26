package com.inventorsoft.crm.javacrm;

import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.domain.service.LeadService;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
@ActiveProfiles("test")
public abstract class AbstractTest {
    protected static final String PUBLIC_PREFIX = "/public-api/";
    protected static final String PRIVATE_PREFIX = "/private-api/";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserService userService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected LeadService leadService;

    @Autowired
    private WebApplicationContext context;

    protected static final String CREATED_USER_EMAIL = "user1234@gmail.com";
    protected static final String CREATED_USER_PASSWORD = "user1234";

    protected MockMvc getSecureMockMvc() {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    protected void createUser() {
        User user = new User();
        user.setEmail(CREATED_USER_EMAIL);
        user.setPassword(passwordEncoder.encode(CREATED_USER_PASSWORD));
        userService.save(user);
    }

    protected Lead createLead() {
        Lead lead = new Lead();
        lead.setFirstName("Test First Name");
        lead.setLastName("Test Last Name");
        lead.setCompanyName("Test Company");
        lead.setCompanySize(50);
        lead.setCountry("Test Country");
        lead.setEmail("testemail@gmail.com");
        lead.setIndustry("Test Industry");
        lead.setStatus(LeadStatus.IN_PROGRESS);
        lead.setNote("Test Note");
        lead.setWebsite("testwebsite.com");
        leadService.save(lead);
        return lead;
    }
}
