package com.mogul.demo.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final UserService userService;
	@Value("${jwt.secret}")
	private String key;
	@Override
	public UserLoginResponse login(UserLoginRequest userLoginRequest) {
		User loginUser = userService.getUserByUsername(userLoginRequest.getUsername());

		userService.passwordEqual(userLoginRequest.getPassword(),loginUser.getPassword());
		//비밀번호 확인하기

		UserLoginResponse userLoginResponse = new UserLoginResponse();

		userLoginResponse.setNickname(loginUser.getNickname());
		userLoginResponse.setToken(JwtTokenUtil.createToken(loginUser,key));

		return userLoginResponse;
	}

	@Override
	public boolean logout() {
		return false;
	}
}
