package com.bank.wealthstream.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bank.wealthstream.exception.TokenInvalidException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.JWT;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String EXPIRATION = "exp";
    public static final String ID = "jti";
    public static final String UNQ_NAME = "unique_name";
    private static final String EXPIRED_TOKEN = "JWT Token expired";
    private static final String INVALID_TOKEN = "JWT Token invalid, can't check user profile";

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    private Map<String, Claim> getAllClaimsFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaims();
    }

    public Boolean isValid(String token) throws TokenExpiredException, TokenInvalidException {
        Map<String, Claim> claims = getAllClaimsFromToken(token);

        final Date expiration = claims.get(EXPIRATION).asDate();

        boolean expired = expiration.before(new Date());
        if (expired) {
            throw new TokenExpiredException(EXPIRED_TOKEN, expiration.toInstant());
        }

        return true;
    }

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    /*public String generateAccessToken(LoginDto loginDto) {
        return Jwts.builder()
                .setSubject( loginDto.getEmail())
                .setIssuer("WealthStream")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }*/

}
