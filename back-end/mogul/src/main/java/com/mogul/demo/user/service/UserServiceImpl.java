package com.mogul.demo.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
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

	@Override
	@Transactional
	public String login(UserLoginRequest userLoginRequest) {
		String email = userLoginRequest.getUsername(); //Get email
		String userId = findUserIdByEmail(email);
		UserRole role = UserRole.USER;

		return (userId == null) ? null : tokenProvider.createToken(userId, role);
	}

	@Override
	@Transactional
	public String findUserIdByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);

		// if(user == null) {
		// 	throw new NoSuchUserException("Cannot find user using {" + email + "}");
		// } else {
		// 	return Long.toString(user.getId());
		// }

		return (user == null) ? null : Long.toString(user.getId());
	}

	@Override
	@Transactional
	public User addUser(UserJoinRequest userJoinRequest) {
		//이메일 중복 체크
		String newEmail = userJoinRequest.getEmail();
		if (userRepository.existsByEmail(newEmail)) {
			return null;
		}

		//닉네임 중복 체크
		String newNickName = userJoinRequest.getNickname();
		if (userRepository.existsByNickname(newNickName)) {
			return null;
		}

		User userToAdd = UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest);

		return userRepository.save(userToAdd);
	}

	@Override
	@Transactional
	public void deleteUser(String userId) {
		User oldUser = userRepository.findById(Long.parseLong(userId)).orElse(null);
		User newUser = new User(
			oldUser.getId(),
			oldUser.getEmail(),
			oldUser.getPassword(),
			oldUser.getNickname(),
			oldUser.getRegisteredDate(),
			new Date(),
			Byte.valueOf("1")
		);

		userRepository.save(newUser);
	}
}
