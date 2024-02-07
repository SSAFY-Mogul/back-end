package com.mogul.demo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserInfoReadResponse;
import com.mogul.demo.user.dto.UserInfoSetRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.exception.DuplicateUserException;
import com.mogul.demo.user.exception.NoSuchUserException;
import com.mogul.demo.user.mapper.UserMapper;
import com.mogul.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserInfoReadResponse getUserInfoById(Long id) throws NoSuchUserException {
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("사용자 정보를 읽어올 수 없습니다.")
			);

		return UserMapper.INSTANCE.userToUserInfoReadResponse(user);
	}

	@Override
	@Transactional
	public UserDto setUserInfo(UserInfoSetRequest userInfoSetRequest) throws NoSuchUserException {
		//Password encoding
		// String oldPassword = userInfoSetRequest.getPassword();
		// userInfoSetRequest.setPassword(passwordEncoder.encode(oldPassword));

		Long id = AuthUtil.getAuthenticationInfoId();
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("사용자 정보를 읽어올 수 없습니다.")
			);

		String oldNickname = user.getNickname();
		String newNickName = userInfoSetRequest.getNickname();


		if (
			!oldNickname.equals(newNickName) //원래 본인 닉네임과 같은 건 상관없다.
				&& userRepository.existsByNickname(newNickName)
		) {
			throw new DuplicateUserException("다른 사용자가 사용 중인 이메일입니다.");
		}

		user.update(userInfoSetRequest);

		return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
	}
}


