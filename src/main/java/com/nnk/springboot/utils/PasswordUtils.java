package com.nnk.springboot.utils;

/** Utility class for password validation. */
public class PasswordUtils {

    private PasswordUtils() {}

    /**
     * Checks whether a password meets the application policy.
     * The password must be at least 8 characters and contain an uppercase letter,
     * a lowercase letter, a digit and a special character.
     *
     * @param password the password string to validate
     * @return true if the password meets policy, false otherwise
     */
    public static boolean isValid(String password) {
        if (password == null || password.isBlank()) return false;
        return password.matches("(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[!@#$%^&*()~`|/\\?.,']).{8,}");
    }
}
