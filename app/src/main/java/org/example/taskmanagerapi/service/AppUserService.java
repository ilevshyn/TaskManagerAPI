package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser findUserById(int id);
    AppUser findUserByUsername(String username);
    AppUser createAppUser(AppUser appUser);
    void deleteAppUser(AppUser appUser);
    List<AppUser> findAllAppUsers();
}
