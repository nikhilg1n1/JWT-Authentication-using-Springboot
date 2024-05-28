package com.jwtauthentication.service;

import com.jwtauthentication.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.websocket.Decoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;

    private final String SECRET="0GJ/PH1iSvU5QjZ3vBDwW68aFd5IomcRnUmtxvl8T3M=";

    public Key getSighInKey(){
        byte[] Keybyte= Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(Keybyte);
    }
    public <T> T extractClaim(String token,Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaim(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaim(String token) {
        return Jwts.parser()
                .setSigningKey(getSighInKey())
                .build()
                .parseEncryptedClaims(token)
                .getBody();
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String ,Object>claims,String username){
        return  Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .setClaims(claims)
                .setSubject(username)
                .signWith(getSighInKey(), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis()+600000))
                .compact();
    }

    public String genrateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,userDetails.getUsername());
    }

    public Boolean isvalideToken(String token,UserDetails userDetails){
        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
