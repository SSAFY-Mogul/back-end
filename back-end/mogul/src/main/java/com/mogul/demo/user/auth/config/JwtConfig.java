package com.mogul.demo.user.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.token.AuthTokenProviderImpl;

@Configuration
public class JwtConfig {
	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.access-token.lifetime}")
	private String accessTokenLifetimeSeconds;

	@Bean
	public AuthTokenProvider authTokenProvider() {
		return new AuthTokenProviderImpl(secretKey, Long.parseLong(accessTokenLifetimeSeconds));
	}
}
