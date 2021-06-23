package com.app.auth.util;

import com.app.auth.service.CustomUserDetail;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${app.jwtSecret}")
    private String secret;

    public String generateToken(Authentication authentication){
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        return Jwts.builder()
                .setSubject(Long.toString(customUserDetail.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public Boolean validateToken(String authToken){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException signatureException){

        }catch (MalformedJwtException malformedJwtException){

        }catch (ExpiredJwtException expiredJwtException){

        }catch (UnsupportedJwtException unsupportedJwtException){

        }catch (IllegalArgumentException illegalArgumentException){

        }
        return false;
    }
}
