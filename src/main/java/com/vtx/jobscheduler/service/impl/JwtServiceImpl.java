package com.vtx.jobscheduler.service.impl;

import com.vtx.jobscheduler.enums.RoleEnum;
import com.vtx.jobscheduler.model.UserDetailsDto;
import com.vtx.jobscheduler.service.JwtService;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private static final int SECRET_KEY_MIN_LENGTH = 32;

    public String generateToken(String username, RoleEnum role) {
        if (secret.length() < SECRET_KEY_MIN_LENGTH) {
            throw new IllegalArgumentException("JWT secret key must be at least 32 characters long");
        }
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.info("Given Token is not valid. Please login again. " +
                    "Error: {} ", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}
