package com.github.xujiaji.mk.common.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 处理api版本映射到对应到注解
 * @author jiajixu
 * @date 2020/11/4 01:20
 */
@Configuration
public class ApiVersionMappingHandlerConfig implements WebMvcRegistrations {

    //请求处理的方法
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        return handlerMapping;
    }

    /**
     * 自定义匹配的处理器
     */
    private static class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        @Override
        protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            return createCondition(apiVersion);
        }

        @Override
        protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            return createCondition(apiVersion);
        }

        private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
            return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
        }
    }
}
