package com.inventorsoft.crm.javacrm.dashboard.service;

import com.inventorsoft.crm.javacrm.dashboard.dictionary.DaysForCount;
import com.inventorsoft.crm.javacrm.dashboard.model.DashboardDto;
import com.inventorsoft.crm.javacrm.domain.service.LeadService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DashboardWebService {

    LeadService leadService;

    @Transactional(readOnly = true)
    public List<DashboardDto> getLeadsByDate(DaysForCount daysForCount) {
        return leadService.getDateAndCountMap(LocalDate.now().minusDays(daysForCount.getDays())).entrySet().stream()
                .map(entry -> new DashboardDto(entry.getKey().format(DateTimeFormatter.ofPattern("dd MMM")), entry.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

}
