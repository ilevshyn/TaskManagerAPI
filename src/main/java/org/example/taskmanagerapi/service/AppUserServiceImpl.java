package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser findUserById(int id) {
        return appUserRepository.findById(id);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        try {
            return appUserRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppUser createAppUser(AppUser appUser) {
        if (appUserRepository.findByUsername(appUser.getUsername()) != null) {
            return null;
        }
        else {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            return appUserRepository.save(appUser);
        }
    }

    @Override
    public void deleteAppUser(AppUser appUser) {
        appUserRepository.delete(appUser);
    }

    @Override
    public List<AppUser> findAllAppUsers() {
        return appUserRepository.findAll();
    }
}