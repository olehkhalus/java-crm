package com.inventorsoft.crm.javacrm.auth.service;

import com.inventorsoft.crm.javacrm.auth.model.SecurityUser;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends OidcUserService {

    UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return Optional.ofNullable(super.loadUser(userRequest).getAttribute("email"))
                .map(String.class::cast)
                .map(String::toLowerCase)
                .flatMap(userService::findByEmail)
                .map(user -> new SecurityUser(user.getEmail(), null))
                .orElseThrow();
    }
}
