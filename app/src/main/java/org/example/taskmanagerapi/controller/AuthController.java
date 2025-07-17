package org.example.taskmanagerapi.controller;

import jakarta.validation.Valid;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.service.LoginService;
import org.example.taskmanagerapi.service.RegisterServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    LoginService loginService;
    RegisterServiceImpl registerService;

    public AuthController(LoginService loginService, RegisterServiceImpl registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> auth(@RequestBody @Valid AppUser user) {
        if (registerService.registerAppUser(user)) {
            return ResponseEntity.ok("Successfully registered user " + user.getUsername());
        } else  {
            return ResponseEntity.status(403).body("User with that username already exists!");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody AppUser user) {
        String loginResult = loginService.login(user);
        if (loginResult != null){
            return ResponseEntity.ok(loginResult);
        } else  {
            return ResponseEntity.status(401).body("Username or password is incorrect!");
        }
    }
}