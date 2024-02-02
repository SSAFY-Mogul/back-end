package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.auth.token.AuthToken;
import com.mogul.demo.user.auth.token.AuthTokenProvider;
import com.mogul.demo.user.dto.UserCheckEmailRequest;
import com.mogul.demo.user.dto.UserCheckNicknameRequest;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;

import io.jsonwebtoken.Claims;
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
	private final AuthTokenProvider authTokenProvider;

	@PostMapping("/login")
	@Operation(
		summary = "로그인",
		description = "이메일과 패스워드로 로그인을 요청하고, 성공 시 응답 header를 통해 JWT를 발급받습니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Authorization: {{accessToken}}"
			),
			@ApiResponse(
				responseCode = "400"
			)
		}
	)
	public ResponseEntity<CustomResponse<Void>> login(
		@RequestBody
		@Valid //UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		String token = userService.login(userLoginRequest);

		ResponseEntity<CustomResponse<Void>> responseEntity = new ResponseEntity<>(
			new CustomResponse<>(
				HttpStatus.BAD_REQUEST.value(),
				null,
				"이메일과 패스워드를 확인하십시오."
			),
			HttpStatus.BAD_REQUEST
		);

		if (token != null) {
			// 로그인 성공한 경우 토큰 발급
			response.setHeader("Authorization", token);
			responseEntity = ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					null,
					"로그인되었습니다."
				)
			);
		}

		return responseEntity;
	}

	@PostMapping("/join")
	public ResponseEntity<CustomResponse<Void>> join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest,
		HttpServletResponse response
	) {
		ResponseEntity<CustomResponse<Void>> responseEntity = new ResponseEntity<>(
			new CustomResponse<>(
				HttpStatus.BAD_REQUEST.value(),
				null,
				"이미 존재하는 이메일 또는 닉네임입니다."
			),
			HttpStatus.BAD_REQUEST
		);

		User user = userService.join(userJoinRequest);
		if (user != null) {
			responseEntity = new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.CREATED.value(),
					null,
					"가입되었습니다."
				),
				HttpStatus.CREATED
			);
		}

		return responseEntity;
	}

	@GetMapping("/duplication/email")
	public ResponseEntity<CustomResponse<Void>> checkDuplicateEmail(
		@RequestBody
		UserCheckEmailRequest request,
		HttpServletResponse response
	) {
		String email = request.getEmail();
		if(userService.isDuplicateEmail(email)) {
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					null,
					"사용 가능한 이메일입니다."
				)
			);
		} else {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					null,
					"사용할 수 없는 이메일입니다."
				),
				HttpStatus.BAD_REQUEST
			);
		}
	}

	@GetMapping("/duplication/nickname")
	public ResponseEntity<CustomResponse<Void>> checkDuplicateNickname(
		@RequestBody
		UserCheckNicknameRequest request,
		HttpServletResponse response
	) {
		String nickname = request.getNickname();
		if(userService.isDuplicateNickname(nickname)) {
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					null,
					"사용 가능한 닉네임입니다."
				)
			);
		} else {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					null,
					"사용할 수 없는 닉네임입니다."
				),
				HttpStatus.BAD_REQUEST
			);
		}
	}


	@PostMapping("/leave")
	public ResponseEntity<CustomResponse<Void>> unregister(
		HttpServletResponse request,
		HttpServletResponse response
	) {
		// 토큰을 받고 토큰을 resolve한다.
		AuthToken token = new AuthToken(request.getHeader("Authorization"));
		Long userId =  userService.getUserIdFromAuthToken(token);

		if(!userService.unregister(Long.toString(userId))) {
			return new ResponseEntity<CustomResponse<Void>>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					null,
					"탈퇴 처리 실패"
				),
				HttpStatus.BAD_REQUEST
			);
		}

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				null,
				"탈퇴 처리되었습니다."
			)
		);
	}
}
