package com.inventorsoft.crm.javacrm.user.profile.controller;

import com.inventorsoft.crm.javacrm.auth.model.SecurityUser;
import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.user.profile.model.PasswordDto;
import com.inventorsoft.crm.javacrm.user.profile.model.ProfileDto;
import com.inventorsoft.crm.javacrm.user.profile.service.ProfileWebService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {

    ProfileWebService profileWebService;

    @GetMapping("/private-api/userProfile")
    public String getUserProfilePage(Model model, @AuthenticationPrincipal SecurityUser securityUser) {
        model.addAttribute("profile", profileWebService.getUserProfile(securityUser));
        return ApplicationConstant.ThymeleafPage.USER_PROFILE;
    }

    @ResponseBody
    @GetMapping("/private-api/userProfile/avatar")
    public String getUserAvatar(@AuthenticationPrincipal SecurityUser securityUser) {
        return profileWebService.getUserAvatar(securityUser);

    }

    @PostMapping("/private-api/userProfile")
    public String updateProfile(@AuthenticationPrincipal SecurityUser securityUser,
                                @Valid @ModelAttribute("profile") ProfileDto profileDto,
                                BindingResult bindingResult) {
        
        return profileWebService.updateProfilePage(securityUser, profileDto, bindingResult)
                ? "redirect:/auth/logout"
                : ApplicationConstant.ThymeleafPage.USER_PROFILE;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/private-api/userProfile/password")
    public void updatePassword(@AuthenticationPrincipal SecurityUser securityUser,
                               @Valid PasswordDto passwordDto) {

        profileWebService.updatePassword(securityUser, passwordDto);
    }
}
