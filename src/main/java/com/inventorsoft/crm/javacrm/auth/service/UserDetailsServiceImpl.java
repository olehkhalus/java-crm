package com.inventorsoft.crm.javacrm.auth.service;

import com.inventorsoft.crm.javacrm.auth.model.SecurityUser;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email.toLowerCase())
                .map(user -> new SecurityUser(user.getEmail(), user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn`t exists"));
    }
}
