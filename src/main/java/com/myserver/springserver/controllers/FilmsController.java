package com.myserver.springserver.controllers;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
import com.myserver.springserver.model.Film;
import com.myserver.springserver.services.implementation.FilmsServiceImpl;
import com.myserver.springserver.util.ResponseJson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/films")
@AllArgsConstructor
public class FilmsController {
    private final FilmsServiceImpl filmsService;

    @GetMapping
    public ResponseEntity<?> getMainPage() {
        try {
            return ResponseEntity.ok(filmsService.getFilms());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getFilm(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(filmsService.getFilm(id));
        } catch (NotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity addFilm(@Validated @RequestBody Film film) {
        try {
            filmsService.addFilm(film);
            return ResponseJson.createSuccessResponse("Film has been added");
        } catch (AlreadyExistException e) {
            return ResponseJson.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFilm(@PathVariable Long id, @RequestBody Film film) {
        try {
            filmsService.updateFilm(id, film);
            return ResponseJson.createSuccessResponse("This film has been updated");
        } catch (NotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFilm(@PathVariable Long id) {
        try {
            filmsService.deleteFilm(id);
            return ResponseJson.createSuccessResponse("Film has been removed");
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAllFilms() {
        try {
            filmsService.deleteAllFilms();
            return ResponseJson.createSuccessResponse("All films have been removed");
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
