package com.example.bankingserver.core.security.jwt;

import com.example.bankingserver.core.security.entity.UserContext;
import com.example.bankingserver.core.user.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final SecretKey jwtSecretKey;

    private SecretKey getJwtSecretKey() {
        return jwtSecretKey;
    }

    public String generateAccessToken(Map<String, Object> claims, int seconds) {
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpiresIn)
                .signWith(getJwtSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authentication");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(Users user) {

        UserContext userContext = new UserContext(user);
        return new UsernamePasswordAuthenticationToken(userContext, null, user.getAuthorities());
    }

    public String getUsername(String token) {

        return (String) Jwts.parserBuilder()
                .setSigningKey(getJwtSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody().get("username");

    }
}
