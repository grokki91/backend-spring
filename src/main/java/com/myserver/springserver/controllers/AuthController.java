package com.myserver.springserver.controllers;

import com.myserver.springserver.dto.SignInRequest;
import com.myserver.springserver.dto.SignUpRequest;
import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.services.AuthService;
import com.myserver.springserver.util.ResponseJson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(authService.signUp(request));
        } catch (AlreadyExistException e) {
            return ResponseJson.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity signIn(@RequestBody SignInRequest request) {
        try {
            return ResponseEntity.ok(authService.signIn(request));
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
