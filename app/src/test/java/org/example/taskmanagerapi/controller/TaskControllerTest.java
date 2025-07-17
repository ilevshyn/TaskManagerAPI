package org.example.taskmanagerapi.controller;

import org.example.taskmanagerapi.jwt.JwtCreateService;
import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.AppUserRepository;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    JwtCreateService jwtCreateService;
    @Autowired
    private AppUserRepository appUserRepository;


    String adminToken;
    String userToken;

    AppUser appUser;
    AppUser appUserAdmin;

    Task task1;
    Task task2;

    @BeforeEach
    void setup() {
        appUserRepository.deleteAll();
        appUser = new AppUser("user", "password", Role.USER);
        appUserAdmin = new AppUser("admin", "password", Role.ADMIN);
        appUserRepository.save(appUser);
        appUserRepository.save(appUserAdmin);
        taskRepository.deleteAll();
        task1 = new Task("title1", "description1", appUserRepository.findById(1), false);
        task2 = new Task("title2", "description2", appUserRepository.findById(1), false);
        taskRepository.save(task1);
        taskRepository.save(task2);
        userToken = jwtCreateService.issueToken(appUser);
        adminToken = jwtCreateService.issueToken(appUserAdmin);
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/tasks")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldNotReturnAllTasks() throws Exception {
        mockMvc.perform(get("/tasks")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 1)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("title1"));
    }

    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .param("title", task1.getTitle())
                        .param("description", task1.getDescription())
                .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("title1"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", task1.getId())
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        mockMvc.perform(put("/tasks/{id}", task1.getId())
                        .param("title", task1.getTitle())
                        .param("description", task1.getDescription())
                        .param("completed", "true")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.completed").value("true"));
    }
}
