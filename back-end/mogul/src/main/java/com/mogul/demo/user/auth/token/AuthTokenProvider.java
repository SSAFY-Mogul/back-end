package com.mogul.demo.user.auth.token;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import com.mogul.demo.user.entity.User;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {
	/*
	 * todo
	 *  1. 토큰 생성
	 *  2. 키 초기화
	 *  3. 유효성 검사
	 *  4. User 정보
	 *  5. 인증 정보
	 *  6. 컨버전
	 *
	 */

	String createToken(String userId, Date expiry);

	Key createKey(@Value("${jwt.secret}") String secret);

	boolean validate(AuthToken token);

	User getUser(AuthToken token);

	Authentication getAuthentication(AuthToken token);

	AuthToken toAuthToken(String tokenString);
}
