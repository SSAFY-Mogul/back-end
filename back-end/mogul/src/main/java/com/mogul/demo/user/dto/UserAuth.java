package com.mogul.demo.user.dto;

import com.mogul.demo.user.role.Role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAuth {
	private final Long id;
	private final Role role;
}
