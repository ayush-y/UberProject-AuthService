package org.example.uberprojectauthservice.Services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private  String SECRET;

    /**
     * This method creates a brand-new JWT token for us based on payload
     */

    private String createToken(Map<String, Object> payload, String email){
        Date date =  new Date();
        Date expiryDate = new Date(date.getTime() + expiry*1000L);

        String token = Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey())
                .compact();
        return token;
    }

    private Claims  extractAllPayloads(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){
            return extractClaim(token, Claims::getExpiration);
    }

    private String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * This method checks if the token expiry was before the current time stamp or not ?
     * @param token JWT token
     * @return true if the Token is expired or else false
     */
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Boolean ValidateToken(String token, String email){
        final String userEmailFetchedFromToken = extractEmail(token);
        return  email.equals(userEmailFetchedFromToken) && !isTokenExpired(token);
    }
    private Object extractPayload(String token, String payloadKey){
        Claims claims = extractAllPayloads(token);
        return (Object) claims.get(payloadKey);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> payload = new HashMap<>();
        payload.put("email", "a@b.com");
        payload.put("phoneNumber", "987373");
        String result = createToken(payload, "ayush");
        System.out.println("Phone Number is  : " + result);
        System.out.println(extractPayload(result, "email").toString());
    }
}
