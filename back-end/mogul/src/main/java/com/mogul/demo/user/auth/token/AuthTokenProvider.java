package com.mogul.demo.user.auth.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.mogul.demo.user.role.UserRole;

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

	String createToken(String userId, UserRole role);

	boolean validate(AuthToken token);

	UserDetails getUser(AuthToken token);

	Long getUserIdFromAuthToken(AuthToken token);

	Long getUserId(AuthToken token);

	Authentication getAuthentication(AuthToken token);

	AuthToken stringToToken(String tokenString);

	String tokenToString(AuthToken token);

	String resolveToken(AuthToken token);
}
