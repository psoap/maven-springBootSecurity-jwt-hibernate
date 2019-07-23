package net.psoap.newsportal.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.psoap.newsportal.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.token.issuer:psoap}")
    private String tokenIssuer;

    @Value("${jwt.signing-key:very-secret}")
    private String jwtSigningKey;

    @Value("${jwt.access.expiration.secs:600}")
    private int jwtExpirationInSecs;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(userDetails.getId().toString());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInSecs * 1000);

        return tokenPrefix + Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey)
                .compact();
    }

    public Long getIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}