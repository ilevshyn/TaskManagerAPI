package org.example.taskmanagerapi.model;

import jakarta.persistence.*;


@Entity
public class Task {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String description;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public Task(String title, String description, AppUser owner, boolean completed) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.completed = completed;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

