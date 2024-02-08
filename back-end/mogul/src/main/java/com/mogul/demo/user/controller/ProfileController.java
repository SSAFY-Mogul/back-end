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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user/profile/")
@RestController
public class ProfileController {
	private final ProfileService profileService;

	private static final int INFO_SIZE = 5;

	@GetMapping("/info")
	public ResponseEntity<CustomResponse<UserInfoReadResponse>> profile(
		//요청은 토큰만
		HttpServletResponse response
	) {
		Long id = AuthUtil.getAuthenticationInfoId();
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
	public ResponseEntity<CustomResponse<String>> setProfile(
		//요청은 토큰만
		@RequestBody
		@Valid
		UserInfoSetRequest userInfoSetRequest,
		HttpServletResponse response
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
