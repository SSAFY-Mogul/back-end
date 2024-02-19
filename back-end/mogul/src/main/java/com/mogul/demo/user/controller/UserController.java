package com.mogul.demo.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserLoginResponse;
import com.mogul.demo.user.dto.UserRegistrationRequest;
import com.mogul.demo.user.dto.UserRegistrationResponse;
import com.mogul.demo.user.service.AuthService;
import com.mogul.demo.user.service.RegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
	private final RegistrationService registrationService;
	private final AuthService authService;

	@PostMapping(value = "/signup")
	public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest userRegistrationRequest){
		UserRegistrationResponse userRegistrationResponse = registrationService.register(userRegistrationRequest);
		return ResponseEntity.ok(userRegistrationResponse);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
		UserLoginResponse userLoginResponse = authService.login(userLoginRequest);
		return ResponseEntity.ok(userLoginResponse);
	}
}
