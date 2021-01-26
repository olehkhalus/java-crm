package com.inventorsoft.crm.javacrm.user.password.service;

import com.inventorsoft.crm.javacrm.domain.model.entity.User;
import com.inventorsoft.crm.javacrm.domain.service.UserService;
import com.inventorsoft.crm.javacrm.user.password.model.UserPasswordChangeDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PasswordWebService {

    UserService userService;
    PasswordEncoder passwordEncoder;

    @Transactional
    public boolean changePassword(UserPasswordChangeDto userPasswordChangeDto) {
        boolean isUpdated;
        Optional<User> userOptional = userService.findByEmail(userPasswordChangeDto.getEmail().toLowerCase());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(userPasswordChangeDto.getPassword()));
            userService.save(user);
            isUpdated = true;
        } else {
            isUpdated = false;
        }
        return isUpdated;
    }
}
