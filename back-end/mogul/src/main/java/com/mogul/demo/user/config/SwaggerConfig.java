package com.mogul.demo.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new io.swagger.v3.oas.models.info.Info()
				.title("Mogul 프로젝트 API")
				.version("v1.0.0"));
	}
}
