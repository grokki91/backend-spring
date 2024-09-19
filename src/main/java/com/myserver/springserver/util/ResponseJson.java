package com.myserver.springserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
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

    public static void authFailHandler(HttpServletResponse response, String body) throws IOException {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("Status", "Error");
        responseMap.put("Message", body);
        String json = new ObjectMapper().writeValueAsString(responseMap);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    public static void authSuccessHandler(HttpServletResponse response, String body) throws IOException {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", body);
        String json = new ObjectMapper().writeValueAsString(responseMap);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
