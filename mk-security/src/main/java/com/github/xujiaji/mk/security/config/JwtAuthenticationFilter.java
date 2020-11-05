package com.github.xujiaji.mk.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.security.exception.SecurityException;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Jwt 认证过滤器
 * </p>
 *
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(MkSecurityConfig.class)
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final MkSecurityConfig mkSecurityConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (mkSecurityConfig.getEnableApiVersion()) {
            // 当前接口的版本号
            final String version = jwtUtil.getVersion(request);

            if (StrUtil.isBlank(version)) {
                ResponseUtil.renderJson(response, Status.NOT_VERSION, null);
                return;
            }
        }

        if (mkSecurityConfig.getEnableApiRequestEncrypt() && needCheckApiRequestEncrypt(request)) {
            // 当前接口的请求时间戳
            final String timestamp = jwtUtil.getTimestamp(request);
            // 当前接口的请求签名
            final String sign = jwtUtil.getSign(request);

            if (StrUtil.isBlank(timestamp)) {
                ResponseUtil.renderJson(response, Status.NOT_TIMESTAMP, null);
                return;
            }

            if (StrUtil.isBlank(sign)) {
                ResponseUtil.renderJson(response, Status.NOT_SIGN, null);
                return;
            }

            if (!jwtUtil.checkSign(sign, timestamp, request)) {
                ResponseUtil.renderJson(response, Status.REQUEST_INVALID, null);
                return;
            }
        }

        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)) {
            try {
                String username = jwtUtil.getUsernameFromJWT(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (SecurityException e) {
                ResponseUtil.renderJson(response, e);
            }
        } else {
            ResponseUtil.renderJson(response, Status.UNAUTHORIZED, null);
        }

    }

    /**
     * 是否需要验证API请求加密信息
     * @param request 请求
     * @return 是否需要验证
     */
    private boolean needCheckApiRequestEncrypt(HttpServletRequest request) {
        if (mkSecurityConfig.getApiRequestEncryptExclude() == null) {
            return true;
        }
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = new HashSet<>();

        switch (httpMethod) {
            case GET:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getHead());
                break;
            case POST:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(mkSecurityConfig.getApiRequestEncryptExclude()
                .getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        if (mkSecurityConfig.getIgnores() == null) {
            return false;
        }
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = new HashSet<>();

        switch (httpMethod) {
            case GET:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getHead());
                break;
            case POST:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(mkSecurityConfig.getIgnores()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(mkSecurityConfig.getIgnores()
                .getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }

}
