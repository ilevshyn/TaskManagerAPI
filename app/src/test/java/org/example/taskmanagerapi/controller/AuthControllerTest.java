package org.example.taskmanagerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppUserRepository appUserRepository;


    @BeforeEach
    void setup() {
        appUserRepository.deleteAll();
        appUserRepository.save(new AppUser("user1", "password1", Role.USER));
        appUserRepository.save(new AppUser("user2", "password2", Role.ADMIN));
    }

    @Test
    void shouldRegister() throws Exception {
        AppUser appUser3 = new AppUser("username3", "password3", Role.USER);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser3)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRegister() throws Exception {
        AppUser errorUser = new AppUser("", "passfdsword3", Role.USER);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(errorUser)))
                .andExpect(status().is(400));
    }

    @Test
    void shouldLogin() throws Exception {
        AppUser appUser4 = new AppUser("username4", "password4", Role.USER);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser4)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser4)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotLogin() throws Exception {
        AppUser appUser1 = new AppUser("username4", "password4", Role.USER);
        AppUser appUser2 = new AppUser("username4", "password45", Role.USER);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser1)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser2)))
                .andExpect(status().is(401));
    }
}
