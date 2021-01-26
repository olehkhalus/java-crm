package com.inventorsoft.crm.javacrm.domain.repository;

import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User getById(Long id);

    boolean existsByEmailIgnoringCase(String email);
}
