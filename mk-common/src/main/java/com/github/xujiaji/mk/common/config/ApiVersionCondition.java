package com.github.xujiaji.mk.common.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义url匹配逻辑
 * @author jiajixu
 * @date 2020/11/3 00:12
 */
@RequiredArgsConstructor
@Data
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    //api的版本
    private final String apiVersion;

    //将不同的筛选条件合并
    @Override
    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        // 方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(apiVersionCondition.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        if (apiVersion.equals(httpServletRequest.getHeader("version"))) {
            return this;
        }
        return null;
    }

    //不同筛选条件比较,用于排序
    @Override
    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        val version = httpServletRequest.getHeader("version");
        if (apiVersionCondition.apiVersion.equals(version)) {
            return 1;
        }
        return apiVersionCondition.apiVersion.compareTo(this.apiVersion);
    }
}
