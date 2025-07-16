package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.service.AppUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    AppUserService appUserService;

    public AdminController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/admin/users")
    public List<AppUser> getAllUsers() {
        return appUserService.findAllAppUsers();
    }
}
