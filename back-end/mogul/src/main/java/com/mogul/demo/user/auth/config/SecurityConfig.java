package com.mogul.demo.user.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mogul.demo.user.auth.filter.AuthTokenFilter;
import com.mogul.demo.user.auth.handler.JwtAuthenticationHandler;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.token.AuthTokenProviderImpl;

@Configuration
public class SecurityConfig {
	@Value("${jwt.secret}")
	private String secret;

	private final String[] URL_TO_PERMIT_ALL = new String[] {
		"/user/login",
		"/user/join",
	};

	@Bean
	public AuthTokenProvider authTokenProvider() {
		return new AuthTokenProviderImpl(secret);
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
			);

		return http.build();
	}
}
