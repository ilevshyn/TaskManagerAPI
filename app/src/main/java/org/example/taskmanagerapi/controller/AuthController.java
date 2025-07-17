package org.example.taskmanagerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Register User",
            description = "Registers user with given credentials, returns result of operation",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "text/plain")
                    ),
                    @ApiResponse(
                            responseCode = "403", content = @Content(mediaType = "text/plain")
                    )
            }
    )
    @PostMapping("/auth/register")
    public ResponseEntity<String> auth(@RequestBody @Valid AppUser user) {
        if (registerService.registerAppUser(user)) {
            return ResponseEntity.ok("Successfully registered user " + user.getUsername());
        } else  {
            return ResponseEntity.status(403).body("User with that username already exists!");
        }
    }

    @Operation(
            summary = "Login User",
            description = "Logins user with given credentials, returns bearer token on success",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "text/plain")),
                    @ApiResponse(
                            responseCode = "401", content = @Content(mediaType = "text/plain")
                    )

            }
    )
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