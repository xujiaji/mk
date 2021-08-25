package com.github.xujiaji.mk.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.github.xujiaji.mk.common.service.IFileUrlService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECS = 3600;

    /**
     * DateTime格式化字符串
     */
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date格式化字符串
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Time格式化字符串
     */
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    /**
     * 包含id的字段转string
     */
    private static final String STR_ID = "id";

    private static final String STR_PASSWORD = "password";

    @Autowired(required = false)
    private List<IFileUrlService> fileUrlServices;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 日期类型字符串处理
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATETIME_PATTERN));

        // Java8日期日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        objectMapper.registerModule(javaTimeModule);

        converter.setObjectMapper(objectMapper);
        return converter;
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
                                } else if (javaType.isTypeOrSubTypeOf(String.class)) { // 将是图片的字段id转为图片全路径
                                    if (key.contains(STR_PASSWORD)) {
                                        beanPropertyWriter.assignSerializer(new JsonSerializer<Object>() {

                                            @Override
                                            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                                                if (value == null) {
                                                    return;
                                                }
                                                gen.writeString("******");
                                            }
                                        });
                                    } else if (fileUrlServices != null) {
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

                            }
                            return beanProperties;
                        }
                    });
                    mapper.setSerializerFactory(serializerFactory);
                });
    }
}
