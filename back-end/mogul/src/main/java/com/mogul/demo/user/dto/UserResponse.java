package com.mogul.demo.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
	private final Long id;
	private final String nickname;
}
