package com.mogul.demo.user.auth.token;

import java.time.Duration;

import org.springframework.security.core.Authentication;

import com.mogul.demo.user.role.Role;

import io.jsonwebtoken.ExpiredJwtException;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {

	String createToken(String userId, Role role);

	boolean validate(AuthToken token) throws ExpiredJwtException;

	Long getUserIdFromAuthToken(AuthToken token);

	Duration getRemainingTime(AuthToken token);

	Authentication getAuthentication(AuthToken token);

	String tokenToString(AuthToken token);
}
