package com.mogul.demo.user.service;

import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserJoinResponse;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.role.UserRole;

import jakarta.validation.Valid;

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

	UserLoginResponse login();

	String createToken(String userId, UserRole role);

	UserJoinResponse addUser(UserJoinRequest userJoinRequest);

	void deleteUser(String userId);
}
