package com.inventorsoft.crm.javacrm.config;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/public-api/auth/login")
                .setViewName(ApplicationConstant.ThymeleafPage.LOGIN_PAGE);
    }

}
