package com.myserver.springserver.controllers;

import com.myserver.springserver.services.WelcomeService;
import com.myserver.springserver.util.ResponseJson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class WelcomeController {
    private WelcomeService welcomeService;

    @GetMapping
    public ResponseEntity greeting(String text) {
        try {
            return ResponseJson.createSuccessResponse(welcomeService.greeting(text));
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
