package com.mogul.demo.user.service;

import java.time.Duration;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.auth.exception.UnauthorizedException;
import com.mogul.demo.user.auth.service.RedisService;
import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.exception.DuplicateUserException;
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
	private final RedisService redisService;

	@Override
	@Transactional
	public String login(UserLoginRequest userLoginRequest) {
		String email = userLoginRequest.getEmail(); //Get email

		Long userId = findIdByEmail(email); //해당 계정이 존재함을 확인한다.
		if (userId == null) {
			throw new NoSuchUserException("입력한 계정 정보를 확인해주세요.");
		}

		//패스워드 일치를 확인한다.
		//passwordEncoder로 encode 시 무작위 salt 값이 생성되므로
		//passwordEncoder.matches()로 비교해야 한다.
		String password = userLoginRequest.getPassword();
		String userPassword = findPasswordById(userId);
		if (!passwordEncoder.matches(password, userPassword)) {
			throw new NoSuchUserException("입력한 계정 정보를 확인해주세요.");
		}

		return tokenProvider.createToken(Long.toString(userId), Role.USER);
	}

	@Override
	@Transactional
	public Long findIdByEmail(String email) {
		User user = userRepository
			.findByEmail(email)
			.orElseThrow(
				() -> new NoSuchUserException("해당 이메일을 사용하는 사용자가 없습니다.")
			);

		return user.getId();
	}

	@Override
	public String findPasswordById(Long id) {
		User user = userRepository
			.findById(id)
			.orElseThrow(
				() -> new NoSuchUserException("해당 ID를 사용하는 사용자 정보가 없습니다.")
			);

		return user.getPassword();
	}

	@Override
	@Transactional
	public UserDto join(UserJoinRequest userJoinRequest) {
		//이메일 중복 체크
		String newEmail = userJoinRequest.getEmail();
		if (isDuplicateEmail(newEmail)) {
			throw new DuplicateUserException("이미 사용 중인 이메일입니다.");
		}

		//닉네임 중복 체크
		String newNickName = userJoinRequest.getNickname();
		if (isDuplicateNickname(newNickName)) {
			throw new DuplicateUserException("이미 사용 중인 닉네임입니다.");
		}

		//패스워드 인코딩
		String newPassword = passwordEncoder.encode(userJoinRequest.getPassword());
		userJoinRequest.setPassword(newPassword);

		return UserMapper.INSTANCE.userToUserDto(
			userRepository.save(
				UserMapper.INSTANCE.userJoinRequestToUser(userJoinRequest)
			)
		);
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
			throw new NoSuchUserException("존재하지 않는 사용자입니다");
		}

		if (userToDelete.getIsDeleted() == 1 && userToDelete.getDeletedDate() != null) {
			throw new NoSuchUserException("이미 탈퇴된 사용자입니다.");
		}

		User deletedUser = userToDelete.softDelete();

		userRepository.save(deletedUser);
		return true;
	}

	@Override
	public boolean logout(AuthToken token) {
		//1. userId, 토큰 값, 남은 시간을 token으로부터 뽑아낸다.
		Long userId = tokenProvider.getUserIdFromAuthToken(token);
		String value = tokenProvider.tokenToString(token);
		Duration expiry = tokenProvider.getRemainingTime(token);

		//2. 뽑아낸 데이터를 redis에 저장한다(키가 같으면 갱신된다).
		redisService.revoke(userId, value, expiry);

		//3. SecurityContext에 등록된 인증 정보를 삭제한다.
		SecurityContextHolder.clearContext();

		return false;
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

	@Override
	public User getUserFromAuth() throws UnauthorizedException {
		Long id = AuthUtil.getAuthenticationInfoId();
		return findUserById(id);
	}
}
