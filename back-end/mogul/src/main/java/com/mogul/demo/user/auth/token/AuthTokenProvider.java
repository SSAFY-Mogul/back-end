package com.mogul.demo.user.auth.token;

import java.time.Duration;

import org.springframework.security.core.Authentication;

import com.mogul.demo.user.role.Role;

import io.jsonwebtoken.ExpiredJwtException;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {
	/*
	 * todo
	 *  1. 토큰 생성
	 *  2. 키 초기화(생성자에서 함)
	 *  3. 유효성 검사
	 *  4. User 정보
	 *  5. 인증 정보(안 쓸 듯 함)
	 *  6. 컨버전(토큰 -> 문자열, 문자열 -> 토큰)
	 */

	String createToken(String userId, Role role);

	boolean validate(AuthToken token) throws ExpiredJwtException;

	Long getUserIdFromAuthToken(AuthToken token);

	Duration getRemainingTime(AuthToken token);

	Authentication getAuthentication(AuthToken token);

	String tokenToString(AuthToken token);

	String resolveToken(AuthToken token);
}
