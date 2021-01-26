package com.inventorsoft.crm.javacrm.auth;

import com.inventorsoft.crm.javacrm.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


class AuthenticationTest extends AbstractTest {

    @Transactional
    @Test
    void formLoginAndRedirectTest() throws Exception {
        createUser();
        getSecureMockMvc().perform(SecurityMockMvcRequestBuilders.formLogin(PUBLIC_PREFIX + "auth/login")
                .user(CREATED_USER_EMAIL)
                .password(CREATED_USER_PASSWORD))
                .andExpect(MockMvcResultMatchers.redirectedUrl(PRIVATE_PREFIX + "dashboard"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    void badFormLoginTest() throws Exception {
        getSecureMockMvc().perform(SecurityMockMvcRequestBuilders.formLogin(PUBLIC_PREFIX + "auth/login")
                .user("baduser@mail.com")
                .password("user1234"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(PUBLIC_PREFIX + "auth/login?error"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    @Transactional
    @Test
    void invalidCsrfTest() throws Exception {
        createUser();
        getSecureMockMvc().perform(MockMvcRequestBuilders.post(PUBLIC_PREFIX + "auth/login").with(SecurityMockMvcRequestPostProcessors.csrf().useInvalidToken())
                .param("username", CREATED_USER_EMAIL)
                .param("password", CREATED_USER_PASSWORD))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void anonymousUserRedirectToLoginTest() throws Exception {
        getSecureMockMvc().perform(MockMvcRequestBuilders.get(PRIVATE_PREFIX + "dashboard").with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void logoutTest() throws Exception {
        getSecureMockMvc().perform(SecurityMockMvcRequestBuilders.logout())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost" + PUBLIC_PREFIX + "auth/login"));
    }
}