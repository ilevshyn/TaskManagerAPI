package org.example.taskmanagerapi.repository;

import org.example.taskmanagerapi.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);
    AppUser findById(int id);
}
