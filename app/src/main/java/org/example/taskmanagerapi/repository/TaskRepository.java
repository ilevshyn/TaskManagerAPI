package org.example.taskmanagerapi.repository;

import org.example.taskmanagerapi.model.AppUser;
import org.example.taskmanagerapi.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<List<Task>> findByOwnerId(int ownerId);
}


