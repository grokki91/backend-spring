package com.myserver.springserver.controllers;

import com.myserver.springserver.dto.ChangePasswordRequest;
import com.myserver.springserver.exception.AlreadyExistException;
import com.myserver.springserver.model.MyUser;
import com.myserver.springserver.security.JwtCore;
import com.myserver.springserver.services.UserService;
import com.myserver.springserver.util.ResponseJson;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    private JwtCore jwtCore;

    private final static String ACCESS_DENIED = "Access denied";

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            if (!jwtCore.isCurrentIdMatch(id)) {
                return ResponseJson.createErrorResponse(HttpStatus.FORBIDDEN, ACCESS_DENIED);
            }

            return ResponseEntity.ok(userService.getUser(id));
        } catch (UsernameNotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity addUser(@RequestBody MyUser user) {
        try {
            userService.save(user);
            return ResponseJson.createSuccessResponse("User has been added");
        } catch (AlreadyExistException e) {
            return ResponseJson.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody MyUser user) {
        try {
            if (!jwtCore.isCurrentIdMatch(id)) {
                return ResponseJson.createErrorResponse(HttpStatus.FORBIDDEN, ACCESS_DENIED);
            }

            userService.updateUser(id, user);
            return ResponseJson.createSuccessResponse("User has been updated");
        } catch (AlreadyExistException e) {
            return ResponseJson.createErrorResponse(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseJson.createSuccessResponse("User has been removed");
        } catch (UsernameNotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseJson.createSuccessResponse("All users have been removed");
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/change-password/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest req) {
        try {
            if (!jwtCore.isCurrentIdMatch(id)) {
                return ResponseJson.createErrorResponse(HttpStatus.FORBIDDEN, ACCESS_DENIED);
            }

            userService.changePassword(id, req.getPassword(), req.getNewPassword(), req.getConfirmPassword());
            return ResponseJson.createSuccessResponse("Password has been successfully changed");
        } catch (UsernameNotFoundException e) {
            return ResponseJson.createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            return ResponseJson.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
