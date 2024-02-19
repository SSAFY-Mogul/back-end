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

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.sessionManagement(
				(session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests(
				auth -> auth
					.requestMatchers(HttpMethod.POST,"/api/user/signup/**").permitAll()
					.requestMatchers(HttpMethod.POST,"/api/user/login/**").permitAll()
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