package com.mogul.demo.user.service;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserResponse;
import com.mogul.demo.user.entity.User;

public interface UserService {
	String login(UserLoginRequest userLoginRequest);

	Long findIdByEmail(String email);

	String findPasswordById(Long id);

	UserDto join(UserJoinRequest userJoinRequest);

	boolean isDuplicateEmail(String email);

	boolean isDuplicateNickname(String nickname);

	void unregister(String userId);

	void logout(AuthToken token);

	UserResponse findUserResponseById(Long id);

	User findUserById(Long id);

	User getUserFromAuth();
}
