package com.ecom.auth.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt-secret}")
    private  String secret;

    @Value("${app.jwt-expiry}")
    private  long expiration;

    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .subject(username)
                .signWith(key, Jwts.SIG.HS512)
                .expiration(expiryDate)
                .issuedAt(now)
        .compact();
    }

    public String parseToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
