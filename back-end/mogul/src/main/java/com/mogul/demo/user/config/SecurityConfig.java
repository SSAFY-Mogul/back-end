package com.mogul.demo.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mogul.demo.user.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;
	private String[] POST_URL = {"/api/user/signup/**","/api/user/login/**",};
	private String[] GET_URL = {"/api/board/**","/api/search/**","/api/webtoon/**","/api/library/**"};
	//auth가 필요없는 API를 작성합니다
	private String[] GET_URL_AUTH = {"/api/(board|review)(/[^/]+)*/my"};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.sessionManagement(
				(session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests(
				auth -> auth
					.requestMatchers(HttpMethod.POST,POST_URL).permitAll()
					.requestMatchers(HttpMethod.GET,GET_URL).permitAll()
					.requestMatchers(HttpMethod.GET,GET_URL_AUTH).authenticated()
					.anyRequest().authenticated()
			)
			.addFilterBefore(
				jwtFilter, UsernamePasswordAuthenticationFilter.class
			);

		return httpSecurity.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


}