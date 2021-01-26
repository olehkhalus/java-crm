package com.inventorsoft.crm.javacrm.user.password;

import com.inventorsoft.crm.javacrm.AbstractTest;
import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.user.password.controller.PasswordController;
import com.inventorsoft.crm.javacrm.user.password.model.UserPasswordChangeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * {@link PasswordController}
 */
class PasswordControllerTest extends AbstractTest {

    /**
     * {@link PasswordController#changePassword(UserPasswordChangeDto, BindingResult)}
     */
    @Transactional
    @Test
    void changePasswordTest() throws Exception {
        createUser();
        String newPassword = "user123456";
        mockMvc.perform(MockMvcRequestBuilders.post(PUBLIC_PREFIX + "changePassword")
                .param("email", "user1234@gmail.com")
                .param("password", newPassword)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(passwordEncoder.matches(newPassword, userService.findByEmail("user1234@gmail.com").map(User::getPassword).orElseThrow()));

    }

    /**
     * {@link PasswordController#changePassword(UserPasswordChangeDto, BindingResult)}
     */
    @Transactional
    @Test
    void changePasswordErrorsTest() throws Exception {
        String badEmail = "user1234";
        String badPassword = "123";
        mockMvc.perform(MockMvcRequestBuilders.post(PUBLIC_PREFIX + "changePassword")
                .param("email", badEmail)
                .param("password", badPassword)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "password"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "email"));
    }

    /**
     * {@link PasswordController#changePassword(UserPasswordChangeDto, BindingResult)}
     */
    @Transactional
    @Test
    void testChangePasswordForNonExistingEmail() throws Exception {
        String newPassword = "user123456";
        String badEmail = "usernotexist@gmail.com";
        mockMvc.perform(MockMvcRequestBuilders.post(PUBLIC_PREFIX + "changePassword")
                .param("email", badEmail)
                .param("password", newPassword)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertTrue(userService.findByEmail(badEmail).isEmpty());
    }
}