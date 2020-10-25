package com.github.xujiaji.mk.security.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan("com.github.xujiaji.mk.*.mapper")
@ComponentScans(
		value = {@ComponentScan("com.github.xujiaji.mk.*.util"),
				@ComponentScan("com.github.xujiaji.mk.*.config")}
)
public class MkSecurityAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkSecurityAdminApplication.class, args);
	}

}
