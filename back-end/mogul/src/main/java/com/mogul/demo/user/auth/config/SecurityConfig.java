package com.mogul.demo.user.auth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mogul.demo.user.auth.entrypoint.JwtAuthenticationEntryPoint;
import com.mogul.demo.user.auth.filter.AuthTokenFilter;
import com.mogul.demo.user.auth.handler.JwtAccessDeniedHandler;
import com.mogul.demo.user.auth.token.AuthTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthTokenProvider tokenProvider;

	//인증 토큰 없이 쓸 수 있는 것들
	private final String[] PERMIT_ALL = new String[] {
		"/api/user/login",
		"/api/user/join",
		"/api/v3/api-docs/**", "/api/swagger-ui/**", "/api/swagger-resources/**",
		"/configuration/**"
	};

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Content-type", "accessToken", "Set-cookie"));
		// corsConfiguration.addAllowedOrigin("http://localhost:3000");
		corsConfiguration.setAllowCredentials(Boolean.TRUE);
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setMaxAge(3600L); //1h
		corsConfiguration.setAllowedHeaders(
			Arrays.asList(
				"Origin",
				"X-Requested-With",
				"Content-Type",
				"Accept",
				"Authorization"
			)
		);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter(tokenProvider);
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
		return new JwtAccessDeniedHandler();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Stateless하므로 CSRF 방어 불필요
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(
			(session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);

		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		http
			.authorizeHttpRequests(
				authorizationManagerRequestMatcherRegistry ->
					authorizationManagerRequestMatcherRegistry
						.requestMatchers(PERMIT_ALL).permitAll()
						// .anyRequest().permitAll()
						.anyRequest().authenticated()
			)
			.cors(
				cors -> cors
					.configurationSource(corsConfigurationSource())
			)
			.exceptionHandling(
				configurer -> configurer
					.authenticationEntryPoint(jwtAuthenticationEntryPoint())
					.accessDeniedHandler(jwtAccessDeniedHandler())
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10); //default round
	}
}
