package com.mogul.demo.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(
			"해당 유저는 가입되지 않았습니다."
		));

		return user;
	}
}