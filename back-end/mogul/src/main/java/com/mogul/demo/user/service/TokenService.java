package com.mogul.demo.user.service;

import com.mogul.demo.user.auth.token.AuthToken;

public interface TokenService {
	/*
	 * todo
	 * 1. 토큰 넣기 userId: tokenString
	 * 2. 토큰 만료 기간 설정
	 * 3. userId로 토큰 찾기
	 */

	void insert(AuthToken token);

	String findById(Long id);

}
