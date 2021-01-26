package com.inventorsoft.crm.javacrm.domain.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 60)
    String email;

    String password;

    @Column(length = 20)
    String firstName;

    @Column(length = 20)
    String lastName;

    @Column(length = 200)
    String companyName;

    @Column(length = 200)
    String avatarUrl;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
