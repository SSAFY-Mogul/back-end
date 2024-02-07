package com.mogul.demo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserInfoReadResponse;
import com.mogul.demo.user.dto.UserInfoSetRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.exception.NoSuchUserException;
import com.mogul.demo.user.mapper.UserMapper;
import com.mogul.demo.user.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserInfoReadResponse getUserInfoById(Long id) throws NoSuchUserException {
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("사용자 정보를 읽어올 수 없습니다.")
			);

		return UserMapper.INSTANCE.userToUserInfoReadResponse(user);
	}

	@Override
	public UserDto setUserInfo(@Valid UserInfoSetRequest userInfoSetRequest) throws NoSuchUserException {
		Long id = AuthUtil.getAuthenticationInfoId();
		System.out.println("ID to update: " + id);
		//Password encoding
		String oldPassword = userInfoSetRequest.getPassword();
		userInfoSetRequest.setPassword(passwordEncoder.encode(oldPassword));


		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("사용자 정보를 읽어올 수 없습니다.")
			);
		user.update(userInfoSetRequest);

		return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
	}
}


