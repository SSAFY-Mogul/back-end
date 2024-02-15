package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Slf4j
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
				description = "header를 통해 인증 토큰 반환",
				headers = @Header(name = "Authorization", description = "Access token")
			),
			@ApiResponse(
				responseCode = "400",
				description = "이메일이나 패스워드 형식이 맞지 않거나, DB에 없는 계정 정보인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> login(
		@RequestBody
		@Valid // UserLoginRequest의 NotNull을 검사한다.
		UserLoginRequest userLoginRequest,
		HttpServletResponse response
	) {
		//{nickname, token}
		String[] data = userService.login(userLoginRequest);

		String token = data[1];

		if (token != null) {
			// 로그인 성공한 경우 토큰 발급
			response.setHeader("Authorization", token);
			return ResponseEntity.ok(
				new CustomResponse<>(
					HttpStatus.OK.value(),
					data[0],
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
	@Operation(
		summary = "회원가입",
		description = "이메일, 닉네임, 패스워드로 요청하고, 이미 존재하거나 형식에 맞지 않는 이메일이나 닉네임인지 체크합니다.",

		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "회원가입에 성공한 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "이메일이나 패스워드 형식이 맞지 않거나, 이메일 또는 닉네임이 중복인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> join(
		@RequestBody
		@Valid
		UserJoinRequest userJoinRequest
	) {
		userService.join(userJoinRequest);
		return new ResponseEntity<>(
			new CustomResponse<>(
				HttpStatus.CREATED.value(),
				"",
				"가입되었습니다."
			),
			HttpStatus.CREATED
		);
	}

	@PostMapping("/duplication/email")
	@Operation(
		summary = "이메일 중복체크",
		description = "이미 존재하는 이메일인지 확인합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "이메일이 DB에 없는 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "이메일 형식이 맞지 않거나, 중복인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> checkDuplicateEmail(
		@RequestBody
		@Valid
		UserCheckEmailRequest request
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

	@PostMapping("/duplication/nickname")
	@Operation(
		summary = "닉네임 중복체크",
		description = "이미 존재하는 닉네임인지 확인합니다.",

		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "닉네임이 DB에 없는 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "닉네임 형식이 맞지 않거나, 중복인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> checkDuplicateNickname(
		@RequestBody
		@Valid
		UserCheckNicknameRequest request
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
	@Operation(
		summary = "회원 탈퇴",
		description = "회원 정보를 삭제(된 것으로) 처리합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "삭제 처리에 성공한 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "토큰에 담긴 사용자 정보가 DB에 없거나 이미 삭제 처리된 사용자인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> unregister() {
		Long userId = AuthUtil.getAuthenticationInfoId();
		userService.unregister(Long.toString(userId));

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				"",
				"탈퇴 처리되었습니다."
			)
		);
	}

	@PostMapping("/logout")
	@Operation(
		summary = "로그아웃",
		description = "토큰을 파기 처리합니다. Redis에 저장된 후 발급 시 설정한 만기가 되면 삭제됩니다.\n"
			+ "요청에 포함된 토큰이 Redis에 존재하면 파기된 토큰으로 간주합니다.",

		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "로그아웃 처리에 성공한 경우"
			),
		}
	)
	public ResponseEntity<CustomResponse<String>> logout(
		HttpServletRequest request
	) {
		AuthToken authToken = new AuthToken(request.getHeader("Authorization"));
		log.debug("Token: {}", tokenProvider.tokenToString(authToken));
		try {
			tokenProvider.validate(authToken);
		} catch (JwtException ignored) {
			log.debug("Authtoken is invalid: {}", authToken);
		} finally {
			// 토큰이 이상하더라도 Redis에 등록한다.
			userService.logout(authToken);
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
