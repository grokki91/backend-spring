package com.myserver.springserver.security;

import com.myserver.springserver.model.MyUser;
import com.myserver.springserver.services.implementation.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtCore {
    private SecretKey key;

    @Value("${app.secret}")
    private String secret;

    @Value("${app.expiration}")
    private int expiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof CustomUserDetails customUserDetails) {
            MyUser user = customUserDetails.getUser();
            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            claims.put("role", user.getRole());
        }

        return generateToken(claims,userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSecretKey() {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("The length of the secret must be greater than 32 bytes");
        }

        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String extractUserName(String token) {
        return extractAll(token).getSubject();
    }

    private Claims extractAll(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAll(token).getExpiration().before(new Date());
    }
}
