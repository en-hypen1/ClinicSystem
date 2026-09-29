package com.clinic.util;

public class PasswordTest {

    public static void main(String[] args) {

        String password = "admin123";

        String hashedPassword = PasswordUtil.hashPassword(password);

        System.out.println("Original password: " + password);
        System.out.println("Hashed password: " + hashedPassword);

        boolean correct = PasswordUtil.verifyPassword(
                password,
                hashedPassword
        );

        boolean wrong = PasswordUtil.verifyPassword(
                "wrongpassword",
                hashedPassword
        );

        System.out.println("Correct password: " + correct);
        System.out.println("Wrong password: " + wrong);
    }
}