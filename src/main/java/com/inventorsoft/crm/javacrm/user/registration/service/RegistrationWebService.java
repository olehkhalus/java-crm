package com.inventorsoft.crm.javacrm.user.registration.service;

import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import com.inventorsoft.crm.javacrm.user.registration.model.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RegistrationWebService {

    UserService userService;
    PasswordEncoder passwordEncoder;

    @Transactional
    public boolean register(UserDto userDto) {
        boolean status;
        // check if user already registered
        if (userService.findByEmail(userDto.getEmail().toLowerCase()).isPresent()) {
            status = false;
        } else {
            final User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail().toLowerCase());
            user.setCompanyName(userDto.getCompanyName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.save(user);
            status = true;
        }
        return status;
    }

}
