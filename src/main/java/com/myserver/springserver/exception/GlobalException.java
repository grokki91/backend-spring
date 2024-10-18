package com.myserver.springserver.exception;

import com.myserver.springserver.util.ResponseJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity handleJsonMappingException(HttpMessageNotReadableException e) {
        try {
            return ResponseJson.createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        } catch (Exception exception) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }
}
