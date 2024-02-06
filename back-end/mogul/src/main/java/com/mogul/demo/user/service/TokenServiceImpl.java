package com.mogul.demo.user.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;

import io.jsonwebtoken.JwtException;

@Service
public class TokenServiceImpl implements TokenService {
	private final RedisTemplate<Long, String> redisTemplate;
	private final AuthTokenProvider tokenProvider;
	private final ValueOperations<Long, String> valueOperations;

	@Autowired
	public TokenServiceImpl(
		RedisTemplate<Long, String> redisTemplate,
		AuthTokenProvider tokenProvider
	) {
		this.redisTemplate = redisTemplate;
		this.tokenProvider = tokenProvider;
		this.valueOperations =  redisTemplate.opsForValue();
	}

	@Override
	public void insert(AuthToken token) throws JwtException {
		//1. userId를 token으로부터 뽑아낸다.
		//2. userId를 key로, token을 value로 저장한다.
		//3. 만료 시점을 설정한다.
		//4. 이미 userId가 Redis에 존재해도 그냥 수정한다.

		Long key = tokenProvider.getUserIdFromAuthToken(token);
		String value = tokenProvider.tokenToString(token);
		Duration expiry = tokenProvider.getRemainingTime(token);

		// 이미 있으면 그냥 수정됨
		valueOperations.set(key, value, expiry);
	}

	@Override
	public String findById(Long id) {
		return valueOperations.get(id);
	}
}
