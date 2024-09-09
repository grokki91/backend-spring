package com.myserver.springserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseJson {
    public static ResponseEntity<HashMap<String, Object>> createSuccessResponse(String body) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("Status", "Success");
        response.put("Message", body);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<HashMap<String, Object>> createSuccessResponse(HttpStatus status, String body) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("Status", "Success");
        response.put("Message", body);
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<HashMap<String, Object>> createErrorResponse(HttpStatus status, String body) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("Status", "Error");
        response.put("Message", body);
        return ResponseEntity.status(status).body(response);
    }
}
