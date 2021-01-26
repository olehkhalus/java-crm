package com.inventorsoft.crm.javacrm.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@EntityScan(basePackages = {"com.inventorsoft.crm.javacrm.domain.model.entity"})
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = {"com.inventorsoft.crm.javacrm.domain.repository"})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
