package com.mogul.demo.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.exception.DuplicateUserException;
import com.mogul.demo.user.exception.NoSuchUserException;
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
		String email = userLoginRequest.getEmail(); //Get email
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
	public User join(UserJoinRequest userJoinRequest) {
		//이메일 중복 체크
		String newEmail = userJoinRequest.getEmail();
		if (isDuplicateEmail(newEmail)) {
			// throw new DuplicateUserException("이메일 중복: " + newEmail);
			return null;
		}

		//닉네임 중복 체크
		String newNickName = userJoinRequest.getNickname();
		if (isDuplicateNickname(newNickName)) {
			// throw new DuplicateUserException("닉네임 중복: " + newNickName);
			return null;
		}

		User userToAdd = UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest);

		return userRepository.save(userToAdd);
	}

	@Override
	public boolean isDuplicateEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean isDuplicateNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}

	@Override
	@Transactional
	public boolean unregister(String userId) {
		User userToDelete = userRepository.findById(Long.parseLong(userId)).orElse(null);
		if (userToDelete == null) {
			// throw new NoSuchUserException("존재하지 않는 사용자입니다");
			return false;
		}

		if (userToDelete.getIsDeleted() == 1 && userToDelete.getDeletedDate() != null) {
			// throw new NoSuchUserException("이미 탈퇴된 사용자입니다.");
			return false;
		}

		User deletedUser = userToDelete.softDelete();

		userRepository.save(deletedUser);
		return true;
	}

	@Override
	public Long getUserIdFromAuthToken(AuthToken token) {
		return tokenProvider.getUserIdFromAuthToken(token);
	}

	@Override
	public UserResponse findUserResponseById(Long id) {
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("존재하지 않는 사용자입니다")
			);

		return UserMapper.INSTANCE.userToUserResponse(user);
	}
}
