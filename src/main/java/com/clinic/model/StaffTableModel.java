package com.clinic.model;

public class StaffTableModel {

    private final long userId;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final String photo;

    public StaffTableModel(
            long userId,
            String name,
            String email,
            String phone,
            String role,
            String status,
            String photo
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.photo = photo;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoto() {
        return photo;
    }
}
