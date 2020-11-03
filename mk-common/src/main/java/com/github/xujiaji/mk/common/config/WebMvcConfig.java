package com.github.xujiaji.mk.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.xujiaji.mk.common.base.Consts;
import com.github.xujiaji.mk.common.service.IFilePathService;
import com.github.xujiaji.mk.common.service.impl.MkCommonServiceImpl;
import com.google.common.collect.Sets;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    /**
     * 包含图片id的字段转图片全路径
     */
    private static final Set<String> imgKeys = Sets.newHashSet("image", "avatar", "thumb", "img");

    @Resource(name = "mkCommonServiceImpl")
    private MkCommonServiceImpl mkCommonService;
    @Autowired(required = false)
    private IFilePathService filePathService;

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
                                if (javaType.isTypeOrSubTypeOf(Long.class)) {
                                    val s = beanPropertyWriter.getName().toLowerCase();
                                    // 将包含id的Long字段转为String类型
                                    if (s.contains(STR_ID)) {
                                        beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {

                                            @Override
                                            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                                                if (value == null) {
                                                    return;
                                                }
                                                gen.writeString(value.toString());
                                            }
                                        });
                                    } else if (filePathService != null && imgKeys.contains(s)) { // 将是图片的字段id转为图片全路径
                                        beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {
                                            @Override
                                            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
                                                if (value == null) {
                                                    return;
                                                }
                                                gen.writeString(mkCommonService.valueByKey(Consts.ConfigKey.basePath) + filePathService.getPathById((Long) value));
                                            }
                                        });
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
