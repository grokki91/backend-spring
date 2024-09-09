package com.myserver.springserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;
    private AuthenticationFailureHandler failureHandler;

    public CustomAuthFilter(AuthenticationManager authManager, AuthenticationFailureHandler failureHandler) {
        this.authManager = authManager;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getParameter("username"),
                        request.getParameter("password")
                )
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        HashMap<String, Object> data = new HashMap<>();
        data.put("Status", "Error");
        data.put("Message", "User not found or invalid payload");

        ObjectMapper obj = new ObjectMapper();
        response.getOutputStream().println(obj.writeValueAsString(data));
    }
}
