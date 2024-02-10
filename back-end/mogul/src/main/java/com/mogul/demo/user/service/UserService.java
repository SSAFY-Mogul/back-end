package com.mogul.demo.user.service;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;

public interface UserService {
	/*
	 * todo
	 * 토큰 생성
	 * 로그인
	 * 회원가입
	 * 로그아웃
	 * 회원탈퇴
	 * 내 정보 조회
	 * 내 정보 수정
	 * 닉네임 중복체크
	 * */

	String login(UserLoginRequest userLoginRequest);

	Long findIdByEmail(String email);

	String findPasswordById(Long id);

	UserDto join(UserJoinRequest userJoinRequest);

	boolean isDuplicateEmail(String email); // throws DuplicateUserException;

	boolean isDuplicateNickname(String nickname); // throws DuplicateUserException;

	boolean unregister(String userId);

	boolean logout(AuthToken token);

	Long getUserIdFromAuthToken(AuthToken token);

	UserResponse findUserResponseById(Long id);

	User findUserById(Long id);

	User getUserFromAuth();
}
