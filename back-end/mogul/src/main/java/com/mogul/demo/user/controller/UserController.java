package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 기능 API")
public class UserController {
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<CustomResponse> login(
		@RequestBody
		@Valid //UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		CustomResponse<String> userLoginResponse = new CustomResponse<>(
			HttpStatus.OK.value(),
			HttpStatus.OK.getReasonPhrase(),
			"로그인되었습니다"
		);

		response.setHeader("Authentication", userService.login(userLoginRequest));

		return ResponseEntity.ok(userLoginResponse);
	}

	@PostMapping("/join")
	public ResponseEntity<CustomResponse> join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest,
		HttpServletResponse response
	) {

		User user = userService.addUser(userJoinRequest);
		CustomResponse<String> userJoinResponse = new CustomResponse<>(
			HttpStatus.CREATED.value(),
			HttpStatus.CREATED.getReasonPhrase(),
			"가입되었습니다."
		);

		return ResponseEntity.ok(userJoinResponse);
	}

}
