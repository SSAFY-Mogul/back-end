package com.mogul.demo.user.dto;

import com.mogul.demo.user.role.UserRole;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAuth {
	private final String userId;
	private final UserRole role;
}
