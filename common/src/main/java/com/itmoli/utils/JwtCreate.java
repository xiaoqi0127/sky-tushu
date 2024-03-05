package com.itmoli.utils;

import com.itmoli.po.Admin;
import com.itmoli.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtCreate {

    private final JwtProperties jwtProperties;

    public  String getJwt(Admin admin){
        Map<String,Object> claims = new HashMap();
        claims.put("id",admin.getId());
                claims.put("account",admin.getAccount());
        String jwt = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,jwtProperties.getAdminSecretKey())
                .setExpiration(new Date(System.currentTimeMillis()+jwtProperties.getAdminTtl()))
                .compact();
        return jwt;
    }
}
