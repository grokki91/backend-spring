package com.myserver.springserver.util;

import com.myserver.springserver.model.CharacterEntity;
import com.myserver.springserver.model.Gender;
import com.myserver.springserver.model.MyUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CheckValidation {
    static String ALIAS_REGEX = "^[\\p{L}\\s\\-']+|[0-9]{1,50}$";
    static String NAME_REGEX = "^[\\p{L}\\s\\-']{1,50}$";
    static String ABILITIES_REGEX = "^[\\p{L}\\s,.-]{1,100}$";
    static String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    static String TEAM_REGEX = "^[\\p{L}0-9]+([-\\s][\\p{L}0-9]+){0,49}$";
    static String AGE_REGEX = "^(?:[1-9][0-9]{0,6}|[1-9][0-9]{0,6})$";
    static String SEND_VALUE_GENDER;

    public static void validateCharacter(CharacterEntity character) {

        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }

        List<String> missingFields = new ArrayList<>();

        String alias = character.getAlias();
        String fullName = character.getFull_name();
        String alignment = character.getAlignment();
        String abilities = character.getAbilities();
        Integer age = character.getAge();
        String team = character.getTeam();

        if (alias == null) {
            missingFields.add("alias");
        } else if (!isValidAlias(alias)) {
            throw new IllegalArgumentException("Invalid field Alias");
        }

        if (fullName == null) {
            missingFields.add("full_name");
        } else if (!isValidName(fullName)) {
            throw new IllegalArgumentException("Invalid field Full Name");
        }

        if (alignment == null) {
            missingFields.add("alignment");
        } else if (!alignment.equalsIgnoreCase("good") && !alignment.equalsIgnoreCase("evil") && !alignment.equalsIgnoreCase("neutral")) {
            throw new IllegalArgumentException("Invalid field Alignment");
        }

        if (abilities == null) {
            missingFields.add("abilities");
        } else if (!isValidAbilities(abilities)) {
            throw new IllegalArgumentException("Invalid field Abilities");
        }

        if (age == null) {
            missingFields.add("age");
        } else if (!isValidAge(age)) {
            throw new IllegalArgumentException("Invalid field Age");
        }

        if (team == null) {
            missingFields.add("team");
        } else if (!isValidTeam(team)) {
            throw new IllegalArgumentException("Invalid field Team");
        }

        if (!missingFields.isEmpty()) {
            if (missingFields.size() == 1) {
                throw new IllegalArgumentException("Missing field: " + missingFields.get(0));
            }
            throw new IllegalArgumentException("Missing fields: " + String.join(", ", missingFields));
        }
    }

    public static void validateUser(MyUser user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String username = user.getUsername();
        String email = user.getEmail();
        Gender gender = user.getGender();
//        LocalDate birthday = user.getBirthday();

        if (username == null || !isValidName(username)) {
            throw new IllegalArgumentException("Invalid field Username");
        }

        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid field Email");
        }

        if (gender == null || !isValidGender(gender)) {
            throw new IllegalArgumentException("Invalid field Gender");
        }
    }

    private static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private static boolean isValidGender(Gender gender) {
        return gender.name().equalsIgnoreCase(SEND_VALUE_GENDER);
    }

//    private static boolean isValidBirthday(String sendValue, Gender gender) {
//        return gender.name().equalsIgnoreCase(sendValue);
//    }

    private static boolean isValidAlias(String name) {
        Pattern pattern = Pattern.compile(ALIAS_REGEX);
        return pattern.matcher(name).matches();
    }

    private static boolean isValidAbilities(String abilities) {
        Pattern pattern = Pattern.compile(ABILITIES_REGEX);
        return pattern.matcher(abilities).matches();
    }

    private static boolean isValidAge(Integer age) {
        Pattern pattern = Pattern.compile(AGE_REGEX);
        return pattern.matcher(age.toString()).matches();
    }

    private static boolean isValidTeam(String team) {
        Pattern pattern = Pattern.compile(TEAM_REGEX);
        return pattern.matcher(team).matches();
    }
}
