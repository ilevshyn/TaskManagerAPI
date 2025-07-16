package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    AppUserRepository appUserRepository;

    public AdminController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/admin/users")
    public List<AppUser> getAllUsers() {
        System.out.println();
        return appUserRepository.findAll();
    }
}
