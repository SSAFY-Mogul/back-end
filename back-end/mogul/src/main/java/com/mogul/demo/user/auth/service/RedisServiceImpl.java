package com.mogul.demo.user.auth.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
	private final RedisTemplate<Long, String> redisTemplate;

	@Override
	@Transactional
	public void revoke(Long userId, String value, Duration expiry) {
		redisTemplate.opsForValue().set(userId, value, expiry);
	}

	@Override
	@Transactional
	public String findBykey(Long key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	@Transactional
	public boolean existsBykey(Long key) {
		return (findBykey(key) != null);
	}

	@Override
	@Transactional
	public boolean delete(Long key) {
		return Boolean.TRUE.equals(redisTemplate.delete(key));
	}
}
