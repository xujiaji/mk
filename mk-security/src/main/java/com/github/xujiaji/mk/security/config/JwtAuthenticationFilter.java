package com.github.xujiaji.mk.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.xujiaji.mk.common.base.Status;
import com.github.xujiaji.mk.security.service.impl.SecUserServiceImpl;
import com.github.xujiaji.mk.security.util.JwtUtil;
import com.github.xujiaji.mk.security.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.xujiaji.mk.security.exception.SecurityException;

/**
 * <p>
 * Jwt 认证过滤器
 * </p>
 *
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomSecurityConfig customSecurityConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)) {
            try {
                String secUserId = jwtUtil.getSecUserIdFromJWT(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(secUserId);
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
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = new HashSet<>();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getHead());
                break;
            case POST:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customSecurityConfig.getIgnores()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customSecurityConfig.getIgnores()
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
