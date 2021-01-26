package com.inventorsoft.crm.javacrm.user.profile.service;

import com.inventorsoft.crm.javacrm.auth.model.SecurityUser;
import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import com.inventorsoft.crm.javacrm.user.profile.model.PasswordDto;
import com.inventorsoft.crm.javacrm.user.profile.model.ProfileDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProfileWebService {

    UserService userService;
    PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ProfileDto getUserProfile(SecurityUser securityUser) {
        User user = userService.findByEmail(securityUser.getUsername()).orElseThrow();
        ProfileDto profile = new ProfileDto();
        profile.setEmail(user.getEmail());
        profile.setFirstName(user.getFirstName());
        profile.setLastName(user.getLastName());
        profile.setCompanyName(user.getCompanyName());
        profile.setAvatarUrl(user.getAvatarUrl());
        return profile;
    }

    private boolean updateProfile(SecurityUser securityUser, ProfileDto profileDto) {
        User user = userService.findByEmail(securityUser.getUsername()).orElseThrow();
        user.setEmail(profileDto.getEmail().toLowerCase());
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setCompanyName(profileDto.getCompanyName());
        user.setAvatarUrl(profileDto.getAvatarUrl());
        userService.save(user);
        return !securityUser.getUsername().equals(profileDto.getEmail());
    }

    @Transactional
    public void updatePassword(SecurityUser securityUser, PasswordDto passwordDto) {
        User user = userService.findByEmail(securityUser.getUsername()).orElseThrow();
        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        userService.save(user);
    }

    @Transactional
    public boolean updateProfilePage(SecurityUser securityUser, ProfileDto profileDto, BindingResult bindingResult) {
        boolean logoutUser = false;
        if (!bindingResult.hasErrors()) {
            if (!securityUser.getUsername().equals(profileDto.getEmail()) &&
                    userService.existsByEmailIgnoringCase(profileDto.getEmail())) {
                bindingResult.rejectValue("email", "profile.email", "This email address is already in use");
            } else {
                logoutUser = updateProfile(securityUser, profileDto);
            }
        }
        return logoutUser;
    }

    @Transactional(readOnly = true)
    public String getUserAvatar(SecurityUser securityUser) {
        return userService.findByEmail(securityUser.getUsername())
                .map(User::getAvatarUrl)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public String getName(SecurityUser securityUser) {
        return userService.findByEmail(securityUser.getUsername())
                .map(User::getFullName)
                .orElseThrow();
    }
}
