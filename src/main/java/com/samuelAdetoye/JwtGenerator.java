package com.samuelAdetoye;

import io.jsonwebtoken.*;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

public class JwtGenerator {

    private static final String SECRET_KEY = generateRandomSecretKey();

    private static String generateRandomSecretKey() {
        byte[] secretKeyBytes = new byte[256 / 8];
        new SecureRandom().nextBytes(secretKeyBytes);
        return Base64.getEncoder().encodeToString(secretKeyBytes);
    }

    public static String generateJwt(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String verifyToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
            return "Verification pass";
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid token or parsing error
            e.printStackTrace();
            return "Verification fails";
        }
    }

//    public static void main(String[] args) {
//        // Example usage
//        String jwt = generateJwt("john.doe");
//        System.out.println("Generated JWT: " + jwt);
//
//        boolean isValid = verifyJwt(jwt);
//        System.out.println("Is JWT valid? " + isValid);
//    }

}
