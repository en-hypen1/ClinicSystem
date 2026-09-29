package com.clinic.model;

import java.time.LocalDateTime;

public class User {

    private long id;
    private String email;
    private String passwordHash;
    private String role;
    private String profilePhoto;
    private boolean active;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(long id, String email, String passwordHash,
                String role, String profilePhoto,
                boolean active, LocalDateTime createdAt) {

        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.profilePhoto = profilePhoto;
        this.active = active;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}