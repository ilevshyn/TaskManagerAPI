package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AdminControllerTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtCreateService jwtCreateService;

    String adminToken;
    String userToken;

    AppUser appUser = new AppUser("user", "password", Role.USER);
    AppUser appUserAdmin = new AppUser("admin", "password", Role.ADMIN);

    @BeforeEach
    public void setup() {
        appUserRepository.deleteAll();
        appUserRepository.save(appUser);
        appUserRepository.save(appUserAdmin);
        userToken = jwtCreateService.issueToken(appUser);
        adminToken = jwtCreateService.issueToken(appUserAdmin);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/admin/users")
                .header("Authorization", "Bearer " + adminToken)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldNotReturnAllUsers() throws Exception {
        mockMvc.perform(get("/admin/users")
                .header("Authorization", "Bearer " + userToken)).andExpect(status().isForbidden());
    }

}
