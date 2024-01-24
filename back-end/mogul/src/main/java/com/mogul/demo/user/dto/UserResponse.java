package com.mogul.demo.user.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", nullable = false)
	private String id;

	@Column(name="user_email", nullable = false)
	private String email;

	@Column(name="user_nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name="user_registered_date", nullable = false)
	private Date registeredDate;

	@Column(name="user_deleted_date", nullable = false)
	private Date deletedDate;

	@Column(name="user_is_deleted")
	private Byte isDeleted = 0;

}
