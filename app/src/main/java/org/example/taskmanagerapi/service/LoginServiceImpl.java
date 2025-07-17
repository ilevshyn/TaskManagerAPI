package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtCreateService jwtCreateService;

    public LoginServiceImpl(AppUserService appUserService, JwtCreateService jwtCreateService, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.jwtCreateService = jwtCreateService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<String> login(AppUser appUser) {
        Optional<AppUser> userInDatabase = appUserService.findUserByUsername(appUser.getUsername());
        if (userInDatabase.isPresent()) {
            boolean passwordMatches = passwordEncoder.matches(appUser.getPassword(), userInDatabase.get().getPassword());
            if (passwordMatches) {
                return jwtCreateService.issueToken(userInDatabase.get());
            } else  {
                return Optional.empty();
            }
        } else  {
            return Optional.empty();
        }
    }
}
