package com.mogul.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(/*exclude = SecurityAutoConfiguration.class*/)
@EnableJpaAuditing // User 엔티티에서 @CreatedDate 사용을 위해 필요한 어노테이션
public class MogulApplication {
	public static void main(String[] args) {
		SpringApplication.run(MogulApplication.class, args);
	}
}
