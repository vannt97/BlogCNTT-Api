package com.example.BlogCNTTApi.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenHelper {
    //    key secretKey = "kieu string" vi du: nguyen trong van 123 - > doi qua key64;
    //    sau do tao duoc secretkey
    private final String strKey64 = "bmd1eWVuIHRyb25nZyB2YW4gMTEyMzQgQGdtYWlsLmNzb20gMDM3NDI1NTc3Ng==";

//   tạo token với dữ liệu dạng object string, giai hạn thời gian hết của token
    public String generateToken(String data,long expiredDate){
        Date now = new Date();
        Date dataExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey64));
        return Jwts.builder().setSubject(data).setIssuedAt(now).setExpiration(dataExpired).signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

//    giải mã token
    public String decodeToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey64));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

//    xác minh token
    public boolean validateToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey64));
        boolean isSuccess = false;
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            isSuccess = true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return isSuccess;
    }
}
