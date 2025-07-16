package org.example.taskmanagerapi.controller;

import jakarta.validation.Valid;
import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.service.AppUserService;
import org.example.taskmanagerapi.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    AppUserService appUserService;
    LoginService loginService;

    public AuthController(AppUserService appUserService, LoginService loginService) {
        this.appUserService = appUserService;
        this.loginService = loginService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> auth(@Valid @RequestBody AppUser user) {
        if (appUserService.createAppUser(user) != null){
            return ResponseEntity.ok("Success");
        } else  {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody AppUser user) {
        return ResponseEntity.ok(loginService.login(user));
    }
}