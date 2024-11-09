package com.myserver.springserver.validation;

import com.myserver.springserver.model.CharacterEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CheckValidationCharacter {
    static String ALIAS_REGEX = "^[\\p{L}\\s\\-']+|[0-9]{1,50}$";
    static String FULLNAME_REGEX = "^[\\p{L}\\s\\-']{1,50}$";
    static String ABILITIES_REGEX = "^[\\p{L}\\s,.-]{1,100}$";
    static String TEAM_REGEX = "^[\\p{L}0-9]+([-\\s][\\p{L}0-9]+){0,49}$";
    static String AGE_REGEX = "^(?:[1-9][0-9]{0,6}|[1-9][0-9]{0,6})$";

    public static void validate(CharacterEntity character) {

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
        } else if (!isValidFullName(fullName)) {
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

    public static void validateAlias(String alias) {
        if (!isValidAlias(alias)) throw new IllegalArgumentException("Invalid field Alias");
    }

    public static void validateFullName(String fullname) {
        if (!isValidFullName(fullname)) throw new IllegalArgumentException("Invalid field Full Name");
    }

    public static void validateAbilities(String abilities) {
        if (!isValidAbilities(abilities)) throw new IllegalArgumentException("Invalid field Abilities");
    }

    public static void validateAge(int age) {
        if (!isValidAge(age)) throw new IllegalArgumentException("Invalid field Age");
    }

    public static void validateTeam(String team) {
        if (!isValidTeam(team)) throw new IllegalArgumentException("Invalid field Team");
    }

    private static boolean isValidFullName(String fullname) {
        Pattern pattern = Pattern.compile(FULLNAME_REGEX);
        return pattern.matcher(fullname).matches();
    }

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
