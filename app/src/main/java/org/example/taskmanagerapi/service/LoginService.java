package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;

import java.util.Optional;

public interface LoginService {
    Optional<String> login(AppUser appUser);
}
