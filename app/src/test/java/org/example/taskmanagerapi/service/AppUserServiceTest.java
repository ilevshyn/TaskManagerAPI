package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    AppUser appUser = new AppUser( "username","password", Role.USER);

    @Test
    void testFindAppUserByUsername() {
        when(appUserRepository.findByUsername("username")).thenReturn(Optional.ofNullable(appUser));
        assertEquals(appUser, appUserRepository.findByUsername("username").get());
    }

    @Test
    void testFindAppUserById(){
        when(appUserRepository.findById(1)).thenReturn(Optional.ofNullable(appUser));
        assertEquals(appUser, appUserRepository.findById(1).get());
    }
}
