package com.inventorsoft.crm.javacrm.dashboard.controller;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.dashboard.dictionary.DaysForCount;
import com.inventorsoft.crm.javacrm.dashboard.service.DashboardWebService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DashboardController {

    DashboardWebService dashboardWebService;

    @GetMapping("/private-api/dashboard")
    public String dashboard(Model model, @RequestParam(value = "days") Optional<DaysForCount> days) {
        model.addAttribute("days", DaysForCount.values());
        model.addAttribute("leadsLineChart", dashboardWebService.getLeadsByDate(days.orElse(DaysForCount.SEVEN)));
        return ApplicationConstant.ThymeleafPage.DASHBOARD_PAGE;
    }

}
