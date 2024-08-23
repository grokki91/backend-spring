package com.myserver.springserver.util;

import com.myserver.springserver.model.Film;

import java.util.regex.Pattern;

public class CheckValidation {
    static String TITLE_REGEX = "^[\\p{L}0-9\\s.,'\"-]{1,50}$";
    static String TIME_REGEX = "^[1-9][0-9]{0,2}$";

    public static void validateFilm(Film film) {

        if (film == null) {
            throw new IllegalArgumentException("Film cannot be null");
        }

        String title = film.getTitle();
        Integer time = film.getTime();

        if (title.trim().isEmpty() || !isValidTitle(title)) {
            throw new IllegalArgumentException("Invalid field Title");
        }

        if (time == null || !isValidTime(time)) {
            throw new IllegalArgumentException("Invalid field Time");
        }
    }

    private static boolean isValidTitle(String title) {
        Pattern pattern = Pattern.compile(TITLE_REGEX);
        return pattern.matcher(title).matches();
    }

    private static boolean isValidTime(Integer time) {
        Pattern pattern = Pattern.compile(TIME_REGEX);
        return pattern.matcher(time.toString()).matches();
    }
}
