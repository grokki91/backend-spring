package com.myserver.springserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public final String HEADER = "Authorization";
    public final String BEARER = "Bearer ";

    @Autowired
    private JwtCore jwtCore;

    @Autowired
    private UserDetailsService userService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.equals("/api/signup") || uri.equals("/api/login") || uri.contains("/swagger-ui") || uri.contains("/v3/api-docs") || uri.contains("/swagger-resources") || uri.contains("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String header = request.getHeader(HEADER);

            if (header == null) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED,"JWT is missing");
                return;
            } else if (!header.startsWith(BEARER)) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED,"JWT doesn't contain a BEARER");
                return;
            }

            String jwt = header.substring(BEARER.length());
            String userName = jwtCore.extractUserName(jwt);

            if (StringUtils.hasText(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userName);

                if (jwtCore.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED,"JWT is already expired");
        } catch (MalformedJwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT is invalid");
        } catch (UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        HashMap<String, Object> body = new HashMap<>();
        body.put("Status", "Error");
        body.put("Message", message);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}