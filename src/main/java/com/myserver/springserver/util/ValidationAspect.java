package com.myserver.springserver.util;

import com.myserver.springserver.model.CharacterEntity;
import com.myserver.springserver.model.MyUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    @Before("execution(* com.myserver.springserver.services..*(..))")
    public void validateBeforeMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof CharacterEntity) {
                CheckValidation.validateCharacter((CharacterEntity) arg);
            } else if (arg instanceof MyUser) {
                CheckValidation.validateUser((MyUser) arg);
            }
        }
    }
}
