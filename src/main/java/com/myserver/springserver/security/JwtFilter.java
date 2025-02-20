package com.myserver.springserver.security;
;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("Request URI: " + uri);

        if (uri.equals("/api/signup") || uri.equals("/api/login") ||
                uri.contains("/swagger-ui") || uri.contains("/v3/api-docs") ||
                uri.contains("/swagger-resources") || uri.contains("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String header = request.getHeader(HEADER);
            System.out.println("Authorization header: " + header);

            if (header == null) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT is missing");
                return;
            } else if (!header.startsWith(BEARER)) {
                sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT doesn't contain a BEARER");
                return;
            }

            String jwt = header.substring(BEARER.length());
            String userName = jwtCore.extractUserName(jwt);
            System.out.println("JWT: " + jwt);
            System.out.println("Extracted username: " + userName);

            if (StringUtils.hasText(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userName);
                System.out.println("UserDetails: " + userDetails);
                System.out.println("Authorities: " + userDetails.getAuthorities());
                boolean isValid = jwtCore.isTokenValid(jwt, userDetails);
                System.out.println("Token valid: " + isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                    System.out.println("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
                } else {
                    sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT is not valid");
                    return;
                }
            } else {
                System.out.println("No username or authentication already exists");
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT is already expired");
        } catch (MalformedJwtException e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "JWT is invalid");
        } catch (UsernameNotFoundException e) {
            sendErrorResponse(response, HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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