package com.mogul.demo.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserLeaveRequest {
	private final String userId;
}
