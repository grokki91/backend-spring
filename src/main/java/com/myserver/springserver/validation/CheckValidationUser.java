package com.myserver.springserver.validation;

import com.myserver.springserver.model.MyUser;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class CheckValidationUser {
    static String USERNAME_REGEX = "^[\\p{L}\\s\\-']{1,50}$";
    static String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static void validate(MyUser user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String username = user.getUsername();
        String email = user.getEmail();
        String gender = user.getGender();
        LocalDate birthday = user.getBirthday();

        if (username == null || !isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid field Username");
        }

        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid field Email");
        }

        if (gender == null || !isValidGender(gender)) {
            throw new IllegalArgumentException("Invalid field Gender");
        }

        if (birthday == null || !isValidBirthday(birthday)) {
            throw new IllegalArgumentException("Invalid field Birthday");
        }
    }

    public static void validateUsername(String username) {
        if (!isValidUsername(username)) throw new IllegalArgumentException("Invalid field Username");
    }

    public static void validateEmail(String email) {
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid field Email");
    }

    public static void validateGender(String gender) {
        if (!isValidGender(gender)) throw new IllegalArgumentException("Invalid field Gender");
    }

    public static void validateBirthday(LocalDate birthday) {
        if (!isValidBirthday(birthday)) throw new IllegalArgumentException("Invalid field Birthday");
    }

    private static boolean isValidUsername(String name) {
        Pattern pattern = Pattern.compile(USERNAME_REGEX);
        return pattern.matcher(name).matches();
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private static boolean isValidGender(String gender) {
        return "MALE".equalsIgnoreCase(gender) || "FEMALE".equalsIgnoreCase(gender);
    }

    private static boolean isValidBirthday(LocalDate birthday) {
        LocalDate now = LocalDate.now();
        int age = Period.between(birthday, now).getYears();
        return age > 0;
    }
}
