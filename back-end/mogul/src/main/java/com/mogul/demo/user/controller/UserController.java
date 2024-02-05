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
import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserCheckEmailRequest;
import com.mogul.demo.user.dto.UserCheckNicknameRequest;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 기능 API")
public class UserController {
	private final UserService userService;
	private final AuthTokenProvider tokenProvider;

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
	public ResponseEntity<CustomResponse<String>> login(
		@RequestBody
		@Valid //UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		String token = userService.login(userLoginRequest);

		if (token != null) {
			// 로그인 성공한 경우 토큰 발급
			response.setHeader("Authorization", token);
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					"",
					"로그인되었습니다."
				)
			);
		} else {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"이메일과 패스워드를 확인하십시오."
				),
				HttpStatus.BAD_REQUEST
			);
		}
	}

	@PostMapping("/join")
	public ResponseEntity<CustomResponse<String>> join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest,
		HttpServletResponse response
	) {
		User user = userService.join(userJoinRequest);

		if (user != null) {
			return new ResponseEntity<>(
				new CustomResponse<String>(
					HttpStatus.CREATED.value(),
					"",
					"가입되었습니다."
				),
				HttpStatus.CREATED
			);
		} else {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"이미 존재하는 이메일 또는 닉네임입니다."
				),
				HttpStatus.BAD_REQUEST
			);
		}
	}

	@GetMapping("/duplication/email")
	public ResponseEntity<CustomResponse<String>> checkDuplicateEmail(
		@RequestBody
		@Valid
		UserCheckEmailRequest request,
		HttpServletResponse response
	) {
		String email = request.getEmail();

		if (userService.isDuplicateEmail(email)) {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"사용할 수 없는 이메일입니다."
				),
				HttpStatus.BAD_REQUEST
			);
		} else {
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					"",
					"사용 가능한 이메일입니다."
				)
			);
		}
	}

	@GetMapping("/duplication/nickname")
	public ResponseEntity<CustomResponse<String>> checkDuplicateNickname(
		@RequestBody
		@Valid
		UserCheckNicknameRequest request,
		HttpServletResponse response
	) {
		String nickname = request.getNickname();

		if (userService.isDuplicateNickname(nickname)) {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"사용할 수 없는 닉네임입니다."
				),
				HttpStatus.BAD_REQUEST
			);
		} else {
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					"",
					"사용 가능한 닉네임입니다."
				)
			);
		}
	}

	@PostMapping("/leave")
	public ResponseEntity<CustomResponse<String>> unregister(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Long userId = null;
		try {
			userId = AuthUtil.getAuthenticationInfoId();
		} catch (Exception e) {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"탈퇴 처리 실패"
				),
				HttpStatus.BAD_REQUEST
			);
		}

		if (!userService.unregister(Long.toString(userId))) {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					"",
					"탈퇴 처리 실패"
				),
				HttpStatus.BAD_REQUEST
			);
		} else {
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					"",
					"탈퇴 처리되었습니다."
				)
			);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<CustomResponse<String>> logout(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		AuthToken authToken = new AuthToken(request.getHeader("Authorization"));

		try {
			tokenProvider.validate(authToken);
		} catch (JwtException ignored) {
			//토큰이 이상하더라도 일단 로그아웃 처리는 한다.
			//즉 헤더에서 삭제하고 레디스에 등록한다.
		} finally {
			response.setHeader("Authorization", ""); //헤더에서 삭제한다.
			//이상한 토큰을 redis에 등록한다.
		}

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				"",
				"로그아웃되었습니다."
			)
		);
	}
}
