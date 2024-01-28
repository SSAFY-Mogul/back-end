package com.mogul.demo.user.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", nullable = false)
	private Long id;

	@Column(name="user_email", nullable = false)
	private String email;

	@Column(name="user_password", nullable = false)
	private String password;

	@Column(name="user_nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name="user_registered_date", nullable = false)
	private Date registeredDate;

	@Column(name="user_deleted_date", nullable = false)
	private Date deletedDate;

	@Column(name="user_is_deleted")
	private Byte isDeleted = 0;
}
