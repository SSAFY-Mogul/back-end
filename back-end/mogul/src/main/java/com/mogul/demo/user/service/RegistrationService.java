package com.mogul.demo.user.service;

import com.mogul.demo.user.dto.UserRegistrationRequest;
import com.mogul.demo.user.dto.UserRegistrationResponse;

/**
 * The interface Registration service.
 */
public interface RegistrationService {
	/**
	 * 회원가입 service
	 *
	 * @param userRegistrationRequest the user registration request
	 * @return the user registration response
	 */
	UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);
}
