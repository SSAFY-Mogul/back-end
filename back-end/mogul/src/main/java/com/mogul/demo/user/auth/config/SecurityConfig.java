package com.mogul.demo.user.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mogul.demo.user.auth.entrypoint.JwtAuthenticationEntryPoint;
import com.mogul.demo.user.auth.filter.AuthTokenFilter;
import com.mogul.demo.user.auth.handler.JwtAccessDeniedHandler;
import com.mogul.demo.user.auth.handler.JwtAuthenticationHandler;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.token.AuthTokenProviderImpl;

import lombok.Getter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// @Value("${jwt.secret}")
	// private String secret;

	private final String[] URL_TO_PERMIT_ALL = new String[] {
		"/user/login",
		"/user/join",
		"/api/**",
		"/swagger-ui/**"
	};

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedOrigin("http://localhost:3000");
		corsConfiguration.setAllowCredentials(Boolean.TRUE);
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setMaxAge(3600L);
		corsConfiguration.setAllowedHeaders(Arrays.asList(
			"Origin",
			"X-Requested-With",
			"Content-Type",
			"Accept",
			"Authorization"
		));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public AuthTokenProvider authTokenProvider() {
		// return new AuthTokenProviderImpl(secret);
		return new AuthTokenProviderImpl();
	}

	@Bean
	public AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter(authTokenProvider());
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return JwtAuthenticationHandler.successHandler();
	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return JwtAuthenticationHandler.failureHandler();
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
			.formLogin(
				form -> form
					.loginPage("/user/login")
					.defaultSuccessUrl("/") //redirect
					.successHandler(successHandler())
					.failureHandler(failureHandler())
			)
			.cors(
				cors -> cors
					.configurationSource(corsConfigurationSource())
			)
			.authorizeHttpRequests(
				authorizationManagerRequestMatcherRegistry ->
					authorizationManagerRequestMatcherRegistry
						.requestMatchers(URL_TO_PERMIT_ALL)
						.permitAll()
			)
			.authorizeHttpRequests(
				authorizationManagerRequestMatcherRegistry ->
					authorizationManagerRequestMatcherRegistry
						.anyRequest()
						.authenticated() //URL_TO_PERMIT_ALL 외에 다른 모든 경로는 인증 정보가 있어야 사용할 수 있다.
			)
			.exceptionHandling(
				configurer -> configurer
					.authenticationEntryPoint(jwtAuthenticationEntryPoint())
					.accessDeniedHandler(jwtAccessDeniedHandler())
			);

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(URL_TO_PERMIT_ALL);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
