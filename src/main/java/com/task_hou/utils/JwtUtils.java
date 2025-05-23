package com.task_hou.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    // 原有方法（支持Long类型用户ID生成令牌）
    public String generateToken(Long userId) {
        byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    // 新增重载方法（支持String类型subject生成令牌，用于管理员登录）
    public String generateToken(String subject) {
        byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKeyBytes))
                    .parseClaimsJws(token)
                    .getBody();
            // 添加日志：输出解析成功的 subject
            System.out.println("JWT 解析成功，subject: " + claims.getSubject());
            return claims;
        } catch (Exception e) {
            // 添加日志：输出解析失败的原因
            System.err.println("JWT 解析失败，token: " + token + "，错误: " + e.getMessage());
            throw e;
        }
    }
}