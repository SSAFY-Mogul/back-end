package com.mogul.demo.user.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * Entity는 public 또는 protected 수준의 no-arg 생성자가 필요한데,
 * protected로 설정할 경우 UserMapper 내부(UserMapperImpl)에서
 * 생성자를 호출할 수 없어져 mapstruct에서 문제가 발생한다.
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long id;

	@Column(name = "user_email", nullable = false, unique = true)
	@Setter
	@Email
	private String email;

	@Column(name = "user_password", nullable = false)
	@Setter
	private String password;

	@Column(name = "user_nickname", nullable = false, unique = true)
	@Setter
	private String nickname;

	@Column(name = "user_profile_image", nullable = false, unique = true)
	private String profileImage;


	@Column(name = "user_registered_date", nullable = false)
	@CurrentTimestamp
	private LocalDateTime registeredDate;

	@Column(name = "user_deleted_date", nullable = false)
	private LocalDateTime deletedDate;

	@Column(name = "user_is_deleted")
	private Byte isDeleted = (byte) 0;

	public User softDelete() {
		deletedDate = LocalDateTime.now();
		isDeleted = (byte) 1;

		return this;
	}
}
