package com.mogul.demo.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserInfoReadResponse;
import com.mogul.demo.user.dto.UserInfoSetRequest;
import com.mogul.demo.user.service.ProfileService;
import com.mogul.demo.util.CustomResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/api/user/profile/")
@RestController
@Tag(name = "Profile", description = "사용자 프로필 기능 API")
@Slf4j
public class ProfileController {
	private final ProfileService profileService;

	@GetMapping("/info")
	@Operation(
		summary = "회원 정보 조회",
		description = "회원 정보를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "회원 정보 불러오기에 성공한 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "존재하지 않는 회원의 정보를 조회하려 한 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<UserInfoReadResponse>> profile(
		//요청은 토큰만
	) {
		Long id = AuthUtil.getAuthenticationInfoId();
		log.debug("Current logged in user: {}", id);
		UserInfoReadResponse userInfoReadResponse = profileService.getUserInfoById(id);

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				userInfoReadResponse,
				"사용자 정보를 불러왔습니다."
			)
		);
	}

	@PatchMapping("/info")
	@Operation(
		summary = "회원 정보 수정",
		description = "회원 정보(닉네임)를 수정합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "회원 정보 수정에 성공한 경우"
			),
			@ApiResponse(
				responseCode = "400",
				description = "닉네임이 중복인 경우"
			)
		}
	)
	public ResponseEntity<CustomResponse<String>> setProfile(
		@RequestBody
		@Valid
		UserInfoSetRequest userInfoSetRequest
	) {
		profileService.setUserInfo(userInfoSetRequest);

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				"",
				"사용자 정보가 변경되었습니다."
			)
		);
	}
}
