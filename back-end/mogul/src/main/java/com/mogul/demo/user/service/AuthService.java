package com.mogul.demo.user.service;

import com.mogul.demo.user.dto.UserLoginRequest;
import com.mogul.demo.user.dto.UserLoginResponse;

public interface AuthService {
	UserLoginResponse login(UserLoginRequest userLoginRequest);
	boolean logout();
}