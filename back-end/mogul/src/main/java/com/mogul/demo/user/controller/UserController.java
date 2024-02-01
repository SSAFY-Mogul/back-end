package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLeaveRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 기능 API")
public class UserController {
	private final UserService userService;

	@PostMapping("/login")
	@Operation(
		summary = "로그인",
		description = "이메일과 패스워드로 로그인을 요청하고, 성공 시 응답 header를 통해 JWT를 발급받습니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Authentication: {{accessToken}}"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> login(
		@RequestBody
		@Valid //UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		String token = userService.login(userLoginRequest);

		ResponseEntity<CustomResponse<String>> responseEntity = new ResponseEntity<>(
			new CustomResponse<>(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"이메일과 패스워드를 확인하십시오."
			),
			HttpStatus.BAD_REQUEST
		);

		if (token != null) {
			// 로그인 성공한 경우 토큰 발급
			response.setHeader("Authentication", token);
			responseEntity = ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					HttpStatus.OK.getReasonPhrase(),
					"로그인되었습니다."
				)
			);
		}

		return responseEntity;
	}

	@PostMapping("/join")
	public ResponseEntity<CustomResponse<String>> join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest,
		HttpServletResponse response
	) {
		CustomResponse<String> userJoinResponse = new CustomResponse<>(
			HttpStatus.BAD_REQUEST.value(),
			HttpStatus.BAD_REQUEST.getReasonPhrase(),
			"이미 존재하는 이메일 또는 닉네임입니다."
		);
		ResponseEntity<CustomResponse<String>> responseEntity = new ResponseEntity<>(
			userJoinResponse,
			HttpStatus.BAD_REQUEST
		);

		User user = userService.join(userJoinRequest);
		if (user != null) {
			responseEntity = new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.CREATED.value(),
					HttpStatus.CREATED.getReasonPhrase(),
					"가입되었습니다."
				),
				HttpStatus.CREATED
			);
		}

		return responseEntity;
	}

	@PostMapping("/leave")
	public ResponseEntity<CustomResponse<String>> unregister(
		@RequestBody
		@Valid
		UserLeaveRequest userLeaveRequest,
		HttpServletResponse response
	) {
		// userService.deleteUser(userLeaveRequest.getUserId());

		CustomResponse<String> userLeaveResponse = new CustomResponse<>(
			HttpStatus.OK.value(),
			HttpStatus.OK.getReasonPhrase(),
			"틸퇴되었습니다."
		);

		return ResponseEntity.ok(userLeaveResponse);
	}
}
