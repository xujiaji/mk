package com.github.xujiaji.mk.security.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.security.config.JwtConfig;
import com.github.xujiaji.mk.security.vo.UserPrincipal;
import com.google.common.collect.Lists;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.github.xujiaji.mk.security.exception.SecurityException;

/**
 * <p>
 * JWT 工具类
 * </p>
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param userId      用户userId
     * @param username    用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long userId, String username, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(userId.toString())
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles)
                .claim("authorities", authorities);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        stringRedisTemplate.opsForValue()
                .set(Consts.REDIS_JWT_KEY_PREFIX + username, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getUserId(), userPrincipal.getUsername(), userPrincipal.getRoles(), userPrincipal.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = Consts.REDIS_JWT_KEY_PREFIX + username;

            // 校验redis中的JWT是否存在
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new SecurityException(Status.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = stringRedisTemplate.opsForValue()
                    .get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new SecurityException(Status.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException(Status.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        stringRedisTemplate.delete(Consts.REDIS_JWT_KEY_PREFIX + username);
    }

    /**
     * 根据 jwt 获取userId
     *
     * @param jwt JWT
     * @return 获取secUserId
     */
    public String getUserIdFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getId();
    }

    /**
     * 根据 jwt 获取userId
     *
     * @param jwt JWT
     * @return userId
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 得到接口版本号
     * @param request 请求
     * @return 接口版本号
     */
    public String getVersion(HttpServletRequest request) {
        return request.getHeader("version");
    }

    /**
     * 得到接口请求时间戳
     * @param request 请求
     * @return 接口请求时间戳
     */
    public String getTimestamp(HttpServletRequest request) {
        return request.getHeader("timestamp");
    }

    /**
     * 得到接口请求签名
     * @param request 请求
     * @return 签名
     */
    public String getSign(HttpServletRequest request) {
        return request.getHeader("sign");
    }

    /**
     * 检测签名是否正确
     * @param sign 签名
     * @param timestamp 时间
     * @param request 请求
     * @return 签名是否有效
     */
    private List<String> ignoreParams = Lists.newArrayList();
    public boolean checkSign(String sign, String timestamp, HttpServletRequest request) {
        if (
                !NumberUtil.isLong(timestamp)
                        || timestamp.length() < 10
                        || DateUtil.date().between(new Date(Long.parseLong(timestamp)), DateUnit.SECOND) > 60) {
            return false;
        }
        Map<String, String> params = ServletUtil.getParamMap(request);
        String values = "";
        if (!params.isEmpty()) {
            Optional<String> optional = params.keySet()
                    .stream()
                    .filter(s -> !ignoreParams.contains(s))
                    .sorted()
                    .map(params::get)
                    .reduce((s1, s2) -> String.format("%s%s", s1, s2));
            if (optional.isPresent()) {
                values = optional.get();
            }
        }
        String s = SecureUtil.md5(values + SecureUtil.md5(timestamp.substring(0, 8)) + timestamp);
        return s.equals(sign);
    }
}
