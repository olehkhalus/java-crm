package com.inventorsoft.crm.javacrm.lead.controller;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import com.inventorsoft.crm.javacrm.lead.dto.CreateLeadDTO;
import com.inventorsoft.crm.javacrm.lead.dto.UpdateLeadDTO;
import com.inventorsoft.crm.javacrm.lead.service.LeadWebService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeadController {

    LeadWebService leadWebService;

    @GetMapping("/private-api/leads")
    public String getAllLeads(Model model) {
        model.addAttribute("leads", leadWebService.getAll());
        return ApplicationConstant.ThymeleafPage.LEADS_PAGE;
    }

    @GetMapping("/private-api/leads/create")
    public String getCreateLead(@ModelAttribute("lead") CreateLeadDTO createLeadDTO, Model model) {
        model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
        return ApplicationConstant.ThymeleafPage.CREATE_LEAD_PAGE;
    }

    @PostMapping("/private-api/leads")
    public String saveLead(@Valid @ModelAttribute("lead") CreateLeadDTO createLeadDTO, BindingResult bindingResult, Model model) {
        String path = "redirect:/private-api/leads";
        if (bindingResult.hasErrors()) {
            model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
            path = ApplicationConstant.ThymeleafPage.CREATE_LEAD_PAGE;
        } else if (!leadWebService.create(createLeadDTO)) {
            model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
            bindingResult.rejectValue("email", "lead.email", "There is already lead with this email!");
            path = ApplicationConstant.ThymeleafPage.CREATE_LEAD_PAGE;
        }
        return path;
    }

    @GetMapping("/private-api/leads/edit/{id}")
    public String getUpdateLead(@PathVariable Long id, Model model) {
        model.addAttribute("lead", leadWebService.get(id));
        model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
        return ApplicationConstant.ThymeleafPage.UPDATE_LEAD_PAGE;
    }

    @PatchMapping("/private-api/leads/{id}")
    public String updateLead(@PathVariable Long id, @Valid @ModelAttribute("lead") UpdateLeadDTO updateLeadDTO, BindingResult bindingResult, Model model) {
        String path = "redirect:/private-api/leads";
        if (bindingResult.hasErrors()) {
            model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
            path = ApplicationConstant.ThymeleafPage.UPDATE_LEAD_PAGE;
        } else if (!leadWebService.update(id, updateLeadDTO)) {
            model.addAttribute(ApplicationConstant.LeadConstant.LEAD_STATUSES, LeadStatus.values());
            bindingResult.rejectValue("email", "lead.email", "There is already lead with this email!");
            path = ApplicationConstant.ThymeleafPage.UPDATE_LEAD_PAGE;
        }
        return path;
    }

    @DeleteMapping("/private-api/leads/{id}")
    public String deleteLead(@PathVariable Long id) {
        leadWebService.delete(id);
        return "redirect:/private-api/leads";
    }
}
