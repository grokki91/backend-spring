package com.myserver.springserver.util;

import com.myserver.springserver.model.Film;
import com.myserver.springserver.model.MyUser;

import java.util.regex.Pattern;

public class CheckValidation {
    static String TITLE_REGEX = "^[\\p{L}0-9\\s.,'\"-]{1,50}$";
    static String TIME_REGEX = "^[1-9][0-9]{0,2}$";
    static String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static void validateFilm(Film film) {

        if (film == null) {
            throw new IllegalArgumentException("Film cannot be null");
        }

        String title = film.getTitle();
        Integer time = film.getTime();

        if (title.isEmpty() || !isValidTitle(title)) {
            throw new IllegalArgumentException("Invalid field Title");
        }

        if (time == null || !isValidTime(time)) {
            throw new IllegalArgumentException("Invalid field Time");
        }
    }

    public static void validateUser(MyUser user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String username = user.getUsername();
        String email = user.getEmail();

        if (username == null || !isValidTitle(username)) {
            throw new IllegalArgumentException("Invalid field Username");
        }

        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid field Email");
        }
    }

    private static boolean isValidTitle(String title) {
        Pattern pattern = Pattern.compile(TITLE_REGEX);
        return pattern.matcher(title).matches();
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private static boolean isValidTime(Integer time) {
        Pattern pattern = Pattern.compile(TIME_REGEX);
        return pattern.matcher(time.toString()).matches();
    }
}
