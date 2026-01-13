package com.himashana.dkn.dkn_backend.authentication.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.himashana.dkn.dkn_backend.user.model.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String createToken(Map<String, Object> extraClaims, AppUser appUser){
        return Jwts.builder().setClaims(extraClaims).setSubject(appUser.getEmail()).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1)).signWith(getSigninKey(), SignatureAlgorithm.HS256).compact();
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigninKey() {
        byte[] signinKeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(signinKeyBytes);
    }

    public boolean isExpiredToken(String token){
        Date tokenExpiration = extractClaim(token, Claims::getExpiration);
        return tokenExpiration.before(new Date());
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return(email.equals(userDetails.getUsername())) && !isExpiredToken(token);
    }
}
