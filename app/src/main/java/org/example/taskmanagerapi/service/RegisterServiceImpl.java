package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(AppUserService appUserService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerAppUser(AppUser appUser) {
        if (appUserService.findUserByUsername(appUser.getUsername()).isPresent()) {
            return false;
        } else {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUserService.createAppUser(appUser);
            return true;
        }
    }
}
