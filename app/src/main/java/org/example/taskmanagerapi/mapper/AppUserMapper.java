package org.example.taskmanagerapi.mapper;

import org.example.taskmanagerapi.dto.AppUserDTO;
import org.example.taskmanagerapi.model.AppUser;

public class AppUserMapper {
    public static AppUserDTO toAppUserDTO(AppUser appUser) {
        return new AppUserDTO(appUser.getUsername(), appUser.getRole().toString());
    }
}
