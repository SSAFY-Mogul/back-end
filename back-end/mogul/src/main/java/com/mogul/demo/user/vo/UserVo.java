package com.mogul.demo.user.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserVo {
	private Long id;
	private String email;
	private String password;
	private String nickname;
	private LocalDateTime registeredDate;
	private LocalDateTime deletedDate;
	private Byte isDeleted;
}
