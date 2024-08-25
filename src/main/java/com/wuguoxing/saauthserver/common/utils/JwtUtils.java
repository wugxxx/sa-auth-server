package com.wuguoxing.saauthserver.common.utils;

import com.wuguoxing.saauthserver.common.config.CommonProperties;
import com.wuguoxing.saauthserver.common.exception.BusinessException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 */
public class JwtUtils {

    private static final String SECRET = "blHE3amx7eewKVPrJp6No8U2IthnMnPxhGcfOz6Oq8Q5PMoMlKHp7a2wv1BftzOw";

    private static final SecretKey key = Jwts.SIG.HS256.key()
            .random(new SecureRandom(SECRET.getBytes(StandardCharsets.UTF_8)))
            .build();

    /**
     * 生成 JWT，指定过期时间，精确到秒
     */
    public static String createJWT(String subject, Map<String, Object> claims, Long expire) {
        JwtBuilder builder = Jwts.builder();
        Date now = new Date();
        // 生成token
        builder.issuer("wugx")  // 签发者
                .claims(claims)  // 数据
                .subject(subject)  // 主题
                .issuedAt(now)  // 签发时间
                .expiration(new Date(now.getTime() + expire * 1000))  // 过期时间
                .signWith(key);  // 签名方式
        return builder.compact();
    }

    /**
     * 解析 JWT
     */
    public static Claims parseJWT(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                throw new BusinessException("token已过期");
            }
            if (e instanceof JwtException) {
                throw new BusinessException("token已失效");
            }
            throw new BusinessException("token解析失败");
        }
    }
}