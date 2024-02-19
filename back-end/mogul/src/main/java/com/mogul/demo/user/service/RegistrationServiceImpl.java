package com.mogul.demo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.dto.UserRegistrationRequest;
import com.mogul.demo.user.dto.UserRegistrationResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
		User user = UserMapper.INSTANCE.userRegistrationRequestToUser(userRegistrationRequest);

		userService.duplicateUsername(user.getUsername()); // 이메일 중복 확인

		userService.duplicateNickname(user.getNickname()); // 닉네임 중복 확인

		// front 에서 중복확인을 하지만 한번 더 체크
		// 동시성 이슈가 있을 수 있기 때문

		//비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		UserRegistrationResponse userRegistrationResponse = UserMapper.INSTANCE.userTouserRegistrationResponse(userService.addUser(user));

		return userRegistrationResponse;
	}

}
