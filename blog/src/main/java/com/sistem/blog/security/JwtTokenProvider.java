package com.sistem.blog.security;

import com.sistem.blog.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-milliseconds}")
    private String jwtExpirationInMs;

    public String genToken(Authentication authentication){
        String username = authentication.getName();
        Date actualDate = new Date();
        Date expireDate = new Date(actualDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    public String getUsernameByJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Firma JWT no valida");
        } catch (MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no valida");
        } catch (ExpiredJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT caducado");
        } catch (UnsupportedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        } catch (IllegalArgumentException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "La cadena clams JWT esa vacía");
        }


    }
}
