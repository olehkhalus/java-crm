package com.inventorsoft.crm.javacrm.domain.service;

import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import com.inventorsoft.crm.javacrm.domain.repository.LeadQueryDslRepository;
import com.inventorsoft.crm.javacrm.domain.repository.LeadRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeadService {

    LeadRepository jpaRepository;
    LeadQueryDslRepository queryDslRepository;

    @Transactional(readOnly = true)
    public List<Lead> getAll() {
        return jpaRepository.findAll();
    }

    @Transactional
    public void save(Lead lead) {
        jpaRepository.save(lead);
    }

    @Transactional(readOnly = true)
    public Lead get(Long id) {
        return jpaRepository.getOne(id);
    }

    @Transactional
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Lead> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, Long> getDateAndCountMap(LocalDate localDate) {
        // Map with all LocalDate values
        Map<LocalDate, Long> resultMap = new TreeMap<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; date.isAfter(localDate); date = date.minusDays(1), i++) {
            resultMap.put(date, 0L);
        }
        //fill Map with values from DB
        resultMap.putAll(queryDslRepository.getDateAndCountMap(localDate));
        return resultMap;
    }
}
