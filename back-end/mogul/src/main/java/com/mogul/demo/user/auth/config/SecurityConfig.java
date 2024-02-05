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

	private final String[] PERMIT_ALL = new String[] {
		"/user/login",
		"/user/join",

		"/api/user/login",
		"/api/user/join",
		"/api/duplication/**",

		"/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/swagger-resources/**",
		"/configuration/**"
	};

	private final String[] AUTH_GET = new String[] {
		"/api/webtoon/{webtoon-id}/like",
		"/api/library",
		"/api/library/subscripion"
	};

	private final String[] AUTH_POST = new String[] {
		"/api/webtoon/{webtoon-id}/like",
		"/api/review/",
		"/api/library",
		"/api/library/{library-id}",
		"/api/library/subscription",
		"/api/review/{webtoon-id}"
	};

	private final String[] AUTH_DELETE = new String[] {
		"/api/webtoon/{webtoon-id}/like",
		"/api/library/{library-id}",
		"/api/library/subscription",
		"/api/review/{review-id}"
	};

	private final String[] AUTH_PATCH = new String[] {
		"/api/library/{library-id}",
		"/api/review/{review-id}"
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
						// .requestMatchers(HttpMethod.GET, AUTH_GET).authenticated()
						// .requestMatchers(HttpMethod.POST, AUTH_POST).authenticated()
						// .requestMatchers(HttpMethod.DELETE, AUTH_DELETE).authenticated()
						// .requestMatchers(HttpMethod.PATCH, AUTH_PATCH).authenticated()
						.requestMatchers(PERMIT_ALL).permitAll()
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

