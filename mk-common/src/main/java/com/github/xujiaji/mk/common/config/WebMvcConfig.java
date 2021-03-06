package com.github.xujiaji.mk.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.xujiaji.mk.common.service.IFileUrlService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECS = 3600;

    /**
     * 包含id的字段转string
     */
    private static final String STR_ID = "id";

    @Autowired(required = false)
    private List<IFileUrlService> fileUrlServices;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                .filter(p -> p instanceof MappingJackson2HttpMessageConverter)
                .map(m -> ((MappingJackson2HttpMessageConverter) m).getObjectMapper())
                .forEach(mapper -> {
                    val serializerFactory = mapper.getSerializerFactory().withSerializerModifier(new BeanSerializerModifier() {
                        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                                         List<BeanPropertyWriter> beanProperties) {
                            for (BeanPropertyWriter beanPropertyWriter : beanProperties) {
                                val javaType = beanPropertyWriter.getType();
                                val key = beanPropertyWriter.getName().toLowerCase();
                                // 将包含id的Long字段转为String类型
                                if (javaType.isTypeOrSubTypeOf(Long.class) && key.contains(STR_ID)) {
                                    beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {

                                        @Override
                                        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                                            if (value == null) {
                                                return;
                                            }
                                            gen.writeString(value.toString());
                                        }
                                    });
                                } else if (javaType.isTypeOrSubTypeOf(String.class) && fileUrlServices != null) { // 将是图片的字段id转为图片全路径
                                    for (IFileUrlService fileUrlService : fileUrlServices) {
                                        if (fileUrlService.isEnableUrlAutoFull(key)) {
                                            beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {
                                                @Override
                                                public void serialize(Object value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
                                                    if (value == null) {
                                                        return;
                                                    }
                                                    val url = fileUrlService.getUrlBy(value);
                                                    if (url != null) {
                                                        gen.writeString(url);
                                                    }
                                                }
                                            });
                                            break;
                                        }
                                    }
                                }

                            }
                            return beanProperties;
                        }
                    });
                    mapper.setSerializerFactory(serializerFactory);
                });
    }
}
