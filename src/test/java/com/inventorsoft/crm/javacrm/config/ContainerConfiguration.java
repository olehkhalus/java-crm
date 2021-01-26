//package com.inventorsoft.crm.javacrm.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.testcontainers.containers.PostgreSQLContainer;
//
//import javax.sql.DataSource;
//
//@Configuration(proxyBeanMethods = false)
//public class ContainerConfiguration {
//    @Bean
//    public DataSource dataSource() {
//        var container = new PostgreSQLContainer<>("postgres:13.1");
//        container.start();
//        var config = new HikariConfig();
//        config.setJdbcUrl(container.getJdbcUrl());
//        config.setUsername(container.getUsername());
//        config.setPassword(container.getPassword());
//        return new HikariDataSource(config);
//    }
//}
