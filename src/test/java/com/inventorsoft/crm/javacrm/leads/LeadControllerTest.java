package com.inventorsoft.crm.javacrm.leads;

import com.inventorsoft.crm.javacrm.AbstractTest;
import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import com.inventorsoft.crm.javacrm.lead.controller.LeadController;
import com.inventorsoft.crm.javacrm.lead.dto.CreateLeadDTO;
import com.inventorsoft.crm.javacrm.lead.dto.UpdateLeadDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

/**
 * {@link LeadController}
 */
public class LeadControllerTest extends AbstractTest {

    /**
     * {@link LeadController#saveLead(CreateLeadDTO, BindingResult, Model)}
     */
    @Transactional
    @Test
    void testSaveLead() throws Exception {
        String testFirstName = "Test First Name";
        String testLastName = "Test Last Name";
        String testEmail = "testemail@gmail.com";
        String testCompanyName = "Test Company";
        int testCompanySize = 50;
        String testCountry = "Test Country";
        String testIndustry = "Test Industry";
        LeadStatus testLeadStatus = LeadStatus.IN_PROGRESS;
        String testWebsite = "testwebsite.com";
        String testNote = "Test Note";

        mockMvc.perform(MockMvcRequestBuilders.post(PRIVATE_PREFIX + "leads")
                .param("firstName", testFirstName)
                .param("lastName", testLastName)
                .param("email", testEmail)
                .param("companyName", testCompanyName)
                .param("companySize", String.valueOf(testCompanySize))
                .param("country", testCountry)
                .param("industry", testIndustry)
                .param("status", String.valueOf(testLeadStatus))
                .param("website", testWebsite)
                .param("note", testNote)
        )
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl(PRIVATE_PREFIX + "leads"));

        Lead leadFromDB = leadService.findByEmail(testEmail).orElseThrow();

        Assertions.assertEquals(testFirstName, leadFromDB.getFirstName());
        Assertions.assertEquals(testLastName, leadFromDB.getLastName());
        Assertions.assertEquals(testCompanyName, leadFromDB.getCompanyName());
        Assertions.assertEquals(testCompanySize, leadFromDB.getCompanySize());
        Assertions.assertEquals(testEmail, leadFromDB.getEmail());
        Assertions.assertEquals(testCountry, leadFromDB.getCountry());
        Assertions.assertEquals(testIndustry, leadFromDB.getIndustry());
        Assertions.assertEquals(testLeadStatus, leadFromDB.getStatus());
        Assertions.assertEquals(testNote, leadFromDB.getNote());
    }

    /**
     * {@link LeadController#updateLead(Long, UpdateLeadDTO, BindingResult, Model)}
     */
    @Transactional
    @Test
    void testUpdateLead() throws Exception {
        Lead leadFromDB = createLead();
        String newFirstName = "New First Name";
        String newLastName = "New Last Name";
        String newEmail = "newemail@gmail.com";
        String newCompanyName = "New Company Name";
        int newCompanySize = 30;
        String newCountry = "New Country";
        String newIndustry = "New Industry";
        LeadStatus newLeadStatus = LeadStatus.PAST_CLIENT;
        String newWebsite = "newwebsite.com";
        String newNote = "New Note";

        mockMvc.perform(MockMvcRequestBuilders.patch(PRIVATE_PREFIX + "leads/{id}", leadFromDB.getId())
                .param("firstName", newFirstName)
                .param("lastName", newLastName)
                .param("email", newEmail)
                .param("companyName", newCompanyName)
                .param("companySize", String.valueOf(newCompanySize))
                .param("country", newCountry)
                .param("industry", newIndustry)
                .param("status", String.valueOf(newLeadStatus))
                .param("website", newWebsite)
                .param("note", newNote)
        )
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl(PRIVATE_PREFIX + "leads"));

        Lead updatedLeadFromDB = leadService.findByEmail(newEmail).orElseThrow();

        Assertions.assertEquals(newFirstName, updatedLeadFromDB.getFirstName());
        Assertions.assertEquals(newLastName, updatedLeadFromDB.getLastName());
        Assertions.assertEquals(newCompanyName, updatedLeadFromDB.getCompanyName());
        Assertions.assertEquals(newCompanySize, updatedLeadFromDB.getCompanySize());
        Assertions.assertEquals(newEmail, updatedLeadFromDB.getEmail());
        Assertions.assertEquals(newCountry, updatedLeadFromDB.getCountry());
        Assertions.assertEquals(newIndustry, updatedLeadFromDB.getIndustry());
        Assertions.assertEquals(newLeadStatus, updatedLeadFromDB.getStatus());
        Assertions.assertEquals(newNote, updatedLeadFromDB.getNote());
    }

    /**
     * {@link LeadController#getAllLeads(Model)}
     */
    @Transactional
    @Test
    void testGetAllLeads() throws Exception {
        createLead();
        mockMvc.perform(MockMvcRequestBuilders.get(PRIVATE_PREFIX + "leads"))
                .andExpect(MockMvcResultMatchers.view().name(ApplicationConstant.ThymeleafPage.LEADS_PAGE))
                .andExpect(MockMvcResultMatchers.model().attributeExists( "leads"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * {@link LeadController#getCreateLead(CreateLeadDTO, Model)}
     */
    @Transactional
    @Test
    void testGetCreateLeadPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PRIVATE_PREFIX + "leads/create"))
                .andExpect(MockMvcResultMatchers.view().name(ApplicationConstant.ThymeleafPage.CREATE_LEAD_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * {@link LeadController#getUpdateLead(Long, Model)}
     */
    @Transactional
    @Test
    void testGetUpdateLeadPage() throws Exception {
        Lead leadFromDB = createLead();
        mockMvc.perform(MockMvcRequestBuilders.get(PRIVATE_PREFIX + "leads/edit/{id}", leadFromDB.getId()))
                .andExpect(MockMvcResultMatchers.view().name(ApplicationConstant.ThymeleafPage.UPDATE_LEAD_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * {@link LeadController#deleteLead(Long)}
     */
    @Transactional
    @Test
    void testDeleteLead() throws Exception {
        Lead leadFromDB = createLead();
        mockMvc.perform(MockMvcRequestBuilders.delete(PRIVATE_PREFIX + "leads/{id}", leadFromDB.getId()))
                .andExpect(MockMvcResultMatchers.redirectedUrl(PRIVATE_PREFIX + "leads"));

        Assertions.assertTrue(leadService.findByEmail(leadFromDB.getEmail()).isEmpty());
    }

}
