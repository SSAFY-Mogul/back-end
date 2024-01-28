package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserJoinResponse;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
// @Tag(name = "User", description = "사용자 기능 API")
public class UserController {
	private final UserService userService;

	//userLogin
	@PostMapping("/login")
	public UserLoginResponse login(
		@RequestBody
		@Valid //UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		return userService.login();
	}

	@PostMapping("/join")
	public UserJoinResponse join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest,
		HttpServletResponse response
	) {
		return userService.addUser(userJoinRequest);
	}

}
