package com.mogul.demo.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserJoinDto;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserJoinResponse;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.mapper.UserMapper;
import com.mogul.demo.user.repository.UserRepository;
import com.mogul.demo.user.role.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final AuthTokenProvider tokenProvider;
	private final UserMapper userMapper;
	@Override
	public UserLoginResponse login() {
		UserLoginResponse userLoginResponse = new UserLoginResponse(
			HttpStatus.OK.value(),
			HttpStatus.OK.getReasonPhrase(),
			"로그인되었습니다."
		);
		// response.setHeader("Authorization", authToken.getToken());

		return userLoginResponse;
	}

	@Override
	public String createToken(String userId, UserRole role) {
		return tokenProvider.createToken(userId, role);
	}

	@Override
	public UserJoinResponse addUser(UserJoinRequest userJoinRequest) {
		User userToAdd = userMapper.userJoinRequestToUser(userJoinRequest);

		User user = userRepository.save(userToAdd);

		UserJoinResponse userJoinResponse = new UserJoinResponse(
			HttpStatus.CREATED.value() ,
			HttpStatus.CREATED.getReasonPhrase(),
			"가입되었습니다."
		);

		return userJoinResponse;
	}

	//보류
	@Override
	public void deleteUser(String userId) {

	}
}
