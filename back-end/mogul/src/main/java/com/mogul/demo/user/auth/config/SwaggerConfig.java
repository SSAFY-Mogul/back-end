package com.mogul.demo.user.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
	info =
	@Info(
		title = "Mogul API Document",
		description = "Mango-Jelly 프로젝트의 API 명세서",
		version = "v1"))
@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new io.swagger.v3.oas.models.info.Info()
				.title("Mogul API")
				.version("1.0.0"));
	}
}