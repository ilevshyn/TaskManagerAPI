package org.example.taskmanagerapi.repository;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppUserRepositoryTest {
    @Mock
    private AppUserRepository mockAppUserRepository;

    AppUser mockAppUser = new AppUser(Role.USER, "1234", "user1");

    @Test
    void testAddUser(){
        when(mockAppUserRepository.save(mockAppUser)).thenReturn(mockAppUser);
        mockAppUserRepository.save(mockAppUser);
        when(mockAppUserRepository.findByUsername("user1")).thenReturn(mockAppUser);
        assertEquals(mockAppUser, mockAppUserRepository.findByUsername("user1"));
    }

    @Test
    void testFindUserById(){
        when(mockAppUserRepository.findById(1)).thenReturn(mockAppUser);
        assertEquals(mockAppUser, mockAppUserRepository.findById(1));
    }
}
