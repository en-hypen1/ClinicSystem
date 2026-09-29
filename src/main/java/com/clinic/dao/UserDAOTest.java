package com.clinic.dao;

import com.clinic.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = userDAO.findByEmail("admin@clinicsystem.com");

        if (user != null) {

            System.out.println("USER FOUND!");
            System.out.println("ID: " + user.getId());
            System.out.println("EMAIL: " + user.getEmail());
            System.out.println("ROLE: " + user.getRole());
            System.out.println("ACTIVE: " + user.isActive());

        } else {

            System.out.println("USER NOT FOUND!");
        }
    }
}