package com.mogul.demo.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @OpenAPIDefinition(
// 	info =
// 	@Info(
// 		title = "Mogul API Document",
// 		description = "Mogul 프로젝트의 API 명세서",
// 		version = "v1"))
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
