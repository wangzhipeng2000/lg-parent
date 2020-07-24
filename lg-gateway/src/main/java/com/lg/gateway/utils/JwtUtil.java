package com.lg.gateway.utils;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 */
@Configuration
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key ;

    private long ttl ;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }


    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String key = "eyJqdGkiOiIxIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE1OTUzMTMyODcsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE1OTUzMTMyOTd9";
        System.out.println(key);
        jwtUtil.setKey(key);
        jwtUtil.setTtl(1000*60);
        //生成token
        String token = jwtUtil.createJWT("1", "username", "admin");
        System.out.println(token);
        token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoidXNlcm5hbWUiLCJpYXQiOjE1OTUzMTMzNDMsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE1OTUzMTMzNTN9.I996Iq61EBz8pYl0SB9AoyJQEZwXybABDTeDDvAc2NQ";
        //解析token
        Claims claims = jwtUtil.parseJWT(token);
        claims.getId();
        claims.getSubject();
        Date expiration = claims.getExpiration();
        System.out.println(DateUtil.format(expiration,"yyyy-MM-dd HH:mm:ss"));
        System.out.println(claims);


    }

}
