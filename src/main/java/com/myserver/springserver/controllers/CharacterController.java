package com.myserver.springserver.controllers;

import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.exception.NotFoundException;
import com.myserver.springserver.model.CharacterEntity;
import com.myserver.springserver.services.implementation.CharacterServiceImpl;
import com.myserver.springserver.util.ResponseJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CharacterController {
    private final CharacterServiceImpl characterService;

    @GetMapping
    public ResponseEntity<?> getCharacters() {
        try {
            return ResponseEntity.ok(characterService.getCharacters());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCharacter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(characterService.getCharacter(id));
        } catch (NotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity addCharacter(@Validated @RequestBody CharacterEntity character) {
        try {
            characterService.addCharacter(character);
            return ResponseJson.createSuccessResponse("Character", character.getAlias(), "has been added");
        } catch (AlreadyExistException e) {
            return ResponseJson.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateCharacter(@PathVariable Long id, @RequestBody CharacterEntity character) {
        try {
            characterService.updateCharacter(id, character);
            return ResponseJson.createSuccessResponse("Character has been updated");
        } catch (NotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCharacter(@PathVariable Long id) {
        try {
            CharacterEntity character = characterService.getCharacter(id);
            characterService.deleteCharacter(id);
            return ResponseJson.createSuccessResponse("Character", character.getAlias(),"has been removed");
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAllCharacters() {
        try {
            characterService.deleteAllCharacters();
            return ResponseJson.createSuccessResponse("All characters have been removed");
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
