package com.mogul.demo.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
	private Long id;
	private String email;
	private String password;
	private String nickname;
	private LocalDateTime registeredDate;
	private LocalDateTime deletedDate;
	private Byte isDeleted;
}
