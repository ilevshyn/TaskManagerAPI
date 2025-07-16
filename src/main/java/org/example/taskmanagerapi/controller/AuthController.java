package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder;
    JwtCreateService jwtCreateService;

    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtCreateService jwtCreateService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtCreateService = jwtCreateService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> auth(@RequestBody AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername())==null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            var result = appUserRepository.save(user);
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.status(403).body("User with that username already exists!");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {
        if (appUserRepository.findByUsername(user.getUsername())!=null) {
            var result = appUserRepository.findByUsername(user.getUsername());
            boolean loginResult = passwordEncoder.matches(user.getPassword(), result.getPassword());
            if (loginResult) {
                return ResponseEntity.ok(jwtCreateService.issueToken(result));
            } else  {
                return ResponseEntity.badRequest().build();
            }
        } else {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build();
    }

}
