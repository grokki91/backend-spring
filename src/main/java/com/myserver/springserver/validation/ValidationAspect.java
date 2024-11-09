package com.myserver.springserver.validation;

import com.myserver.springserver.model.CharacterEntity;
import com.myserver.springserver.model.MyUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

@Aspect
@Component
public class ValidationAspect {
    private final String errorMessage = "Invalid payload. Provide at least one field to update";

    @Before("execution(* com.myserver.springserver.services.implementation.CharacterServiceImpl.addCharacter(..))")
    public void validateBeforeCharacterAddMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof CharacterEntity) {
                CheckValidationCharacter.validate((CharacterEntity) arg);
            }
        }
    }

    @Before("execution(* com.myserver.springserver.services.implementation.CharacterServiceImpl.updateCharacter(..))")
    public void validateBeforeCharacterPatchMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof CharacterEntity) {
                CharacterEntity character = (CharacterEntity) arg;
                validateOptionFieldsCharacter(character);
            }
        }
    }

    @Before("execution(* com.myserver.springserver.services.implementation.UserServiceImpl.save(..))")
    public void validateBeforeUserPostMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof MyUser) {
                CheckValidationUser.validate((MyUser) arg);
            }
        }
    }

    @Before("execution(* com.myserver.springserver.services.implementation.UserServiceImpl.updateUser(..))")
    public void validateBeforeUserPatchMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof MyUser) {
                MyUser user = (MyUser) arg;
                validateOptionFieldsUser(user);
            }
        }
    }

    private void validateOptionFieldsCharacter(CharacterEntity character) {
        Stream<? extends Serializable> stream = Stream.of(character.getAlias(), character.getFull_name(), character.getAbilities(), character.getAlignment(), character.getAge(), character.getTeam());
        if (stream.allMatch(Objects::isNull)) {
            throw new IllegalArgumentException(errorMessage);
        };

        if (character.getAlias() != null) {
            CheckValidationCharacter.validateAlias(character.getAlias());
        };

        if (character.getFull_name() != null) {
            CheckValidationCharacter.validateFullName(character.getFull_name());
        };

        if (character.getAbilities() != null) {
            CheckValidationCharacter.validateAbilities(character.getAbilities());
        };

        if (character.getAge() != null) {
            CheckValidationCharacter.validateAge(character.getAge());
        };

        if (character.getTeam() != null) {
            CheckValidationCharacter.validateTeam(character.getTeam());
        };
    }

    private void validateOptionFieldsUser(MyUser user) {
        Stream<? extends Serializable> stream = Stream.of(user.getUsername(), user.getEmail(), user.getPassword(), user.getGender(), user.getBirthday());
        if (stream.allMatch(Objects::isNull)) {
            throw new IllegalArgumentException(errorMessage);
        }

        if (user.getUsername() != null) {
            CheckValidationUser.validateUsername(user.getUsername());
        };

        if (user.getEmail() != null) {
            CheckValidationUser.validateEmail(user.getEmail());
        };

        if (user.getGender() != null) {
            CheckValidationUser.validateGender(user.getGender());
        };

        if (user.getBirthday() != null) {
            CheckValidationUser.validateBirthday(user.getBirthday());
        };
    }
}