package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    AppUserService appUserService;
    PasswordEncoder passwordEncoder;
    JwtCreateService jwtCreateService;

    public LoginServiceImpl(AppUserService appUserService, JwtCreateService jwtCreateService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.jwtCreateService = jwtCreateService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(AppUser appUser) {
        var userInDatabase = appUserService.findUserByUsername(appUser.getUsername());
        boolean passwordMatches = passwordEncoder.matches(appUser.getPassword(), userInDatabase.getPassword());
        if (passwordMatches) {
            return jwtCreateService.issueToken(userInDatabase);
        } else  {
            return null;
        }
    }
}
