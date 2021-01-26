package com.inventorsoft.crm.javacrm.user.registration.controller;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.user.registration.model.UserDto;
import com.inventorsoft.crm.javacrm.user.registration.service.RegistrationWebService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationController {

    RegistrationWebService registrationWebService;

    @GetMapping("public-api/auth/sign-up")
    public String getSignUpPage(@ModelAttribute(value = "user") UserDto userDto) {
        return ApplicationConstant.ThymeleafPage.SIGN_UP_PAGE;
    }

    @PostMapping("public-api/auth/sign-up")
    public String registerUser(@Valid @ModelAttribute(value = "user") UserDto userDto, BindingResult bindingResult) {
        String path = ApplicationConstant.ThymeleafPage.LOGIN_PAGE;
        if (bindingResult.hasErrors()) {
            path = ApplicationConstant.ThymeleafPage.SIGN_UP_PAGE;
        } else if (!registrationWebService.register(userDto)) {
            bindingResult.rejectValue("email", "user.email", "An account already exists for this email.");
            path = ApplicationConstant.ThymeleafPage.SIGN_UP_PAGE;
        }
        return path;
    }
}
