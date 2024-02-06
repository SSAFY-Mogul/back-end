package com.mogul.demo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
// import com.mogul.demo.user.dto.UserInfoResponse;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.exception.NoSuchUserException;
import com.mogul.demo.user.mapper.UserMapper;
import com.mogul.demo.user.repository.UserRepository;
import com.mogul.demo.user.role.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AuthTokenProvider tokenProvider;

	@Override
	@Transactional
	public String login(UserLoginRequest userLoginRequest) {
		String email = userLoginRequest.getEmail(); //Get email

		Long userId = findIdByEmail(email); //해당 계정이 존재함을 확인한다.
		if (userId == null) {
			return null;
		}

		String userPassword = findPasswordById(userId); //패스워드 값이 NULL인지 확인한다.
		if (userPassword == null) {
			return null;
		}

		//패스워드 일치를 확인한다.
		//passwordEncoder로 encode 시 무작위 salt 값이 생성되므로
		//passwordEncoder.matches()로 비교해야 한다.
		String password = userLoginRequest.getPassword();
		if (!passwordEncoder.matches(password, userPassword)) {
			return null;
		}

		return tokenProvider.createToken(Long.toString(userId), Role.USER);
	}

	@Override
	@Transactional
	public Long findIdByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);

		// if(user == null) {
		// 	throw new NoSuchUserException("Cannot find user using {" + email + "}");
		// } else {
		// 	return Long.toString(user.getId());
		// }

		return (user == null) ? null : user.getId();
	}



	@Override
	public String findPasswordById(Long id) {
		User user = userRepository.findById(id).orElse(null);

		return (user == null) ? null : user.getPassword();
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

		//패스워드 인코딩
		String newPassword = passwordEncoder.encode(userJoinRequest.getPassword());
		userJoinRequest.setPassword(newPassword);

		// User userToAdd = UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest);

		return userRepository.save(UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest));
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

	@Override
	public User findUserById(Long id) {
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("존재하지 않는 사용자입니다")
			);

		return user;
	}
}
