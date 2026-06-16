package com.nnk.springboot.utils;

public class PasswordUtils {

    private PasswordUtils() {}

    public static boolean isValid(String password) {
        if (password == null || password.isBlank()) return false;
        return password.matches("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[!@#$%^&*()~`|/\\?.,']).{8,}");
    }
}
