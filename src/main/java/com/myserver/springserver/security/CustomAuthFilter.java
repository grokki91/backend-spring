package com.myserver.springserver.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myserver.springserver.util.ResponseJson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CustomAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtCore jwtCore;

    protected CustomAuthFilter(AuthenticationManager authenticationManager, JwtCore jwtCore) {
        super("/api/login");
        setAuthenticationManager(authenticationManager);
        this.jwtCore = jwtCore;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        /**
         * Convert request to String JSON
         */
        String requestJson = new BufferedReader(new InputStreamReader(request.getInputStream())).lines().collect(Collectors.joining());

        JsonNode json = new ObjectMapper().readTree(requestJson);
        String username = json.get("username").asText();
        String password = json.get("password").asText();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String token = jwtCore.generateToken(userDetails);
        ResponseJson.authSuccessHandler(response, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseJson.authFailHandler(response, "Invalid credentials");
    }
}
