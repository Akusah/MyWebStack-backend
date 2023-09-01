package com.example.common.utils;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.redisson.api.RBucket;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/07/06/下午9:07
 * @Description:
 */
@Component
public class JwtUtils {

    @Resource
    private RedissonClient redissonClient;

    // 令牌秘钥
    @Value("${spring.security.jwt.key}")
    String key;

    // 有效期
    @Value("${spring.security.jwt.expire}")
    int expire;

    /**
     * 让指定Jwt令牌失效
     *
     * @param headerToken 头标记
     * @return boolean
     */
    public boolean invalidateJwt(String headerToken){
        String token = this.covertToken(headerToken);
        if (token == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            String id = jwt.getId();
            return deleteToken(id,jwt.getExpiresAt());
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean deleteToken(String uuid, Date time){
        if (this.isInvalidToken(uuid))
            return false;
        Date now = new Date();
        long expire = Math.max(time.getTime()-now.getTime(),0);
        RMapCache<String,String> mapCache = redissonClient.getMapCache("blacklist");
        mapCache.put(Const.JWT_BLACK_LIST + uuid,"",expire, TimeUnit.MILLISECONDS);
        return true;

    }


    /**
     * 验证Token是否被列入Redis黑名单
     *
     * @param uuid uuid
     * @return boolean
     */

    private boolean isInvalidToken(String uuid){
        // 获取Redis中的RMapCache对象，RMapCache 允许设置键值对的过期时间
        RMapCache<String,String> mapCache = redissonClient.getMapCache("blacklist");
        return mapCache.containsKey(Const.JWT_BLACK_LIST + uuid);

    }


    /**
     * 创建jwt
     *
     * @param details  细节
     * @param id       id
     * @param username 用户名
     * @return {@link String}
     */

    public String createJwt(UserDetails details,int id, String username){
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date expire = this.expireTime();
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString()) //每一个令牌都有一个属于自己的id
                .withClaim("id",id)
                .withClaim("name",username)
                .withClaim("authorities",details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(expire)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }


    /**
     * 解析jwt
     *
     * @param headerToken 头标记
     * @return {@link DecodedJWT}
     */

    public DecodedJWT resolveJwt(String headerToken){
        String token = this.covertToken(headerToken);
        if (token == null) return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);


            //判断jwt令牌是否失效
            if (this.isInvalidToken(jwt.getId())){
                return null;
            }

            Date expiresAt = jwt.getExpiresAt();
            return new Date().after(expiresAt) ? null : jwt;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取到期时间
     *
     * @return {@link Date}
     */

    public Date expireTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,expire*24);
        return calendar.getTime();

    }


    /**
     *
     * 验证前端token格式
     * @param headerToken 头标记
     * @return {@link String}
     */

    private String covertToken(String headerToken){
        if (headerToken == null || !headerToken.startsWith("Bearer "))
            return null;
        return headerToken.substring(7);
    }

    public Integer toId(DecodedJWT jwt){
        Map<String,Claim> claims = jwt.getClaims();
        return claims.get("id").asInt();
    }


    /**
     * 获取用户
     *
     * @param jwt jwt
     * @return {@link UserDetails}
     */

    public UserDetails toUser(DecodedJWT jwt){
        Map<String,Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("name").asString())
                .password("******")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }






}

