package com.myserver.springserver.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.myserver.springserver.util.ResponseJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDate;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity handleJsonMappingException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException formatException = (InvalidFormatException) cause;
            if (formatException.getTargetType().equals(LocalDate.class)) {
                return ResponseJson.createErrorResponse(HttpStatus.BAD_REQUEST,"Invalid field Birthday, required to be in the format - 'YYYY-MM-DD'");
            }
        }

        return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
