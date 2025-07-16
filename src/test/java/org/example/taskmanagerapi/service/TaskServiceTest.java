package org.example.taskmanagerapi.service;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Role;
import org.example.taskmanagerapi.model.Task;
import org.example.taskmanagerapi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    AppUser appUser = new AppUser(Role.ADMIN, "password", "username");
    Task task = new Task("task", "taskdesc", appUser, false);

    @Test
    void testAddTask() {
        when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskRepository.save(task));
    }


}
