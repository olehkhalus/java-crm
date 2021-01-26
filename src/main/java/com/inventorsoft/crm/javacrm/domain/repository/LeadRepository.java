package com.inventorsoft.crm.javacrm.domain.repository;

import com.inventorsoft.crm.javacrm.domain.model.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    Optional<Lead> findByEmail(String email);
}
