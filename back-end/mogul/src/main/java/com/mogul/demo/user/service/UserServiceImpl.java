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
	private final UserMapper userMapper;

	@Override
	@Transactional
	public String login(UserLoginRequest userLoginRequest) {
		String email = userLoginRequest.getUsername(); //Get email
		String userId = findByEmail(email);
		UserRole role = UserRole.ROLE_USER;

		return tokenProvider.createToken(userId, role);
	}

	@Override
	@Transactional
	public String findByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);

		return (user == null) ? null : Long.toString(user.getId());
	}

	@Override
	@Transactional
	public User addUser(UserJoinRequest userJoinRequest) {
		User userToAdd = UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest);
		// userToAdd.getPassword()
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
