package com.triolance.chat.chat_app_backend.JwtUtils;




import com.triolance.chat.chat_app_backend.Entity.Room;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private Room room;

    private final String SECRET_KEY = "ZrUqXFHJoGIb7DSwBKuBOmfOB2k7W4/7quXloT8bVRs=";


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String ExtractUsername(String token){
        String subject = ExtractAllClaims(token).getSubject();
        return subject;
    }

    public Boolean isTokenExpired(String token){
        Date expiration = ExtractAllClaims(token).getExpiration();
        if(expiration.getTime() < new Date(System.currentTimeMillis()).getTime() ){
            generateToken(ExtractUsername(token));
            return true;
        }
        return false;
    }

    public String ExtractRoomId(String token){
        return  extractClaim(token,claims -> claims.get("roomId",String.class));
    }

    private <T>T extractClaim(String token , Function<Claims , T>claimResolver ){
        Claims claims = ExtractAllClaims(token);
        return claimResolver.apply(claims);
    }


    public String generateToken(String username) {
        HashMap<String ,Object>Claims = new HashMap<>();
        Claims.put("roomId",room.getRoomId());

        return createToken(username,Claims);
    }

    public String createToken(String subject, Map<String , Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*5))
                .signWith(getSigningKey())
                .compact();

    }
    public Claims ExtractAllClaims (String token){
        Jws<Claims> claimsJws = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody();
    }


    public Boolean validateToken (String token , String username){
        final String extractedUsername = ExtractUsername(token);

        return ( extractedUsername.equals(username) &&!isTokenExpired(token));
    }



}
