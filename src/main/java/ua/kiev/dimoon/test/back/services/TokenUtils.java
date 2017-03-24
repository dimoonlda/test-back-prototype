package ua.kiev.dimoon.test.back.services;

import io.jsonwebtoken.*;

import java.util.Map;

/**
 * Created by admin on 22.03.2017.
 */
public class TokenUtils {
    public static String key = "abc123";
    public static String generateToken(Map<String, Object> tokenData) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        return token;
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
