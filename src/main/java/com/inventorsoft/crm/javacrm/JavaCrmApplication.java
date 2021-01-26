package com.inventorsoft.crm.javacrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class JavaCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCrmApplication.class, args);
    }
}
