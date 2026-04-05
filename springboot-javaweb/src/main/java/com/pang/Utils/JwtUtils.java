package com.pang.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;


public class JwtUtils {

    private static String signKey = "itheimatjzpsh1234561111111111111111111111231315464656465";
    private static Long  expire = 432000000L;

    /**
     * 生成jWT令牌
     * claims jwt令牌第二部分负载payload中储存的内容
     */

    public static  String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();

        return jwt;
    }


    /**
     * 解析JWT令牌
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey).build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
