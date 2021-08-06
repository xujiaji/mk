package com.github.xujiaji.mk.version.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.github.xujiaji.mk.*.mapper")
@ComponentScans(
		value = {
				@ComponentScan("com.github.xujiaji.mk.*.util"),
				@ComponentScan("com.github.xujiaji.mk.*.config"),
				@ComponentScan("com.github.xujiaji.mk.**.controller")
		}
)
public class MkVersionFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkVersionFrontApplication.class, args);
	}

}
