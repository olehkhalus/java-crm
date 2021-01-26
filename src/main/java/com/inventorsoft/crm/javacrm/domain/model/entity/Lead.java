package com.inventorsoft.crm.javacrm.domain.model.entity;

import com.inventorsoft.crm.javacrm.domain.dictionary.LeadStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String companyName;

    int companySize;

    String website;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    LeadStatus status;

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    String industry;

    String note;

    @CreatedDate
    @Column(nullable = false)
    LocalDate creatingDate;

}
