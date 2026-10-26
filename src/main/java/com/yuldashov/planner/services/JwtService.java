package com.yuldashov.planner.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey jwtAccessSecret;
    private final Long expirationMinutes;

    public JwtService(
            @Value("${jwt.secret}") String jwtAccessSecret,
            @Value("${jwt.expiration-minutes}") Long expirationMinutes) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.expirationMinutes = expirationMinutes;
    }

    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(expirationMinutes, ChronoUnit.MINUTES);
        Date expirationDate = Date.from(expiration);
        return Jwts.builder()
                .subject(username)
                .expiration(expirationDate)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public Claims parseAndValidateAccessToken(String token) {
        return validateToken(token, jwtAccessSecret);
    }


    public Claims validateToken(String token, SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
}
