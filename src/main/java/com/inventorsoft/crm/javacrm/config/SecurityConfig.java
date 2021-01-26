package com.inventorsoft.crm.javacrm.config;

import com.inventorsoft.crm.javacrm.auth.service.CustomOAuth2UserService;
import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ApplicationConstant.PrefixConstants.PUBLIC + "/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage(ApplicationConstant.PrefixConstants.PUBLIC + "/auth/login")
                    .defaultSuccessUrl(ApplicationConstant.PrefixConstants.PRIVATE + "/dashboard", true)
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .oidcUserService(customOAuth2UserService)
                    .and()
                        .defaultSuccessUrl(ApplicationConstant.PrefixConstants.PRIVATE + "/dashboard", true)
                        .failureHandler(this::googleAuthenticationFailureHandler)
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl(ApplicationConstant.PrefixConstants.PUBLIC + "/auth/login");
    }

    private void googleAuthenticationFailureHandler(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    AuthenticationException authenticationException) throws IOException {

        response.sendRedirect(ApplicationConstant.PrefixConstants.PUBLIC + "/auth/login?q=errorWithGoogleAuth");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}