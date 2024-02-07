package com.mogul.demo.user.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
