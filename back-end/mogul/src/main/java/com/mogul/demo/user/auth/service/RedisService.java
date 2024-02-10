package com.mogul.demo.user.auth.service;

import java.time.Duration;

import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.token.AuthToken;

public interface RedisService {
	/*
	 * Redis에는 파기된 토큰(로그아웃한 사용자의 토큰)이 저장된다.
	 */

	void revoke(Long userId, String value, Duration expiry);

	String findBykey(Long key);

	boolean existsBykey(Long key);
}