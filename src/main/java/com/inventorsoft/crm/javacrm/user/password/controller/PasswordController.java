package com.inventorsoft.crm.javacrm.user.password.controller;

import com.inventorsoft.crm.javacrm.common.ApplicationConstant;
import com.inventorsoft.crm.javacrm.user.password.model.UserPasswordChangeDto;
import com.inventorsoft.crm.javacrm.user.password.service.PasswordWebService;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordController {

    PasswordWebService passwordWebService;

    @GetMapping("/public-api/changePassword")
    public String getChangePasswordPage(@ModelAttribute("user") UserPasswordChangeDto userPasswordChangeDto) {
        return ApplicationConstant.ThymeleafPage.CHANGE_PASSWORD;
    }

    @PostMapping("/public-api/changePassword")
    public String changePassword(@Valid @ModelAttribute("user") UserPasswordChangeDto userPasswordChangeDto,
                                 BindingResult bindingResult) {
        String view;
        if (bindingResult.hasErrors()) {
            view = ApplicationConstant.ThymeleafPage.CHANGE_PASSWORD;
        } else {
            boolean isPasswordChanged = passwordWebService.changePassword(userPasswordChangeDto);
            if (isPasswordChanged) {
                view = ApplicationConstant.ThymeleafPage.LOGIN_PAGE;
            } else {
                bindingResult.rejectValue("email", "user.email", "Bad credentials.");
                view = ApplicationConstant.ThymeleafPage.CHANGE_PASSWORD;
            }
        }
        return view;
    }
}
