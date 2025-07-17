package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    AppUser findUserById(int id);
    AppUser findUserByUsername(String username);
    void createAppUser(AppUser appUser);
    void deleteAppUser(AppUser appUser);
    Optional<List<AppUser>> findAllAppUsers();
}
