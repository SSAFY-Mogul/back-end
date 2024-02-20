package com.mogul.demo.user.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Repository
@Entity
@Table(name = "user")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User implements UserDetails {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_email")
	private String username;
	@Column(name = "user_password")
	private String password;
	@Column(name = "user_nickname")
	private String nickname;
	@CreationTimestamp
	@Column(name = "user_registered_date")
	private LocalDateTime registeredDate;
	@Column(name = "user_deleted_date")
	private LocalDateTime deletedDate;
	@Column(name = "user_is_deleted")
	@Builder.Default()
	private Boolean isDeleted = false;
	@Column
	@Builder.Default()
	private Boolean isAccountNonExpired = false;
	@Column
	@Builder.Default()
	private Boolean isAccountNonLocked = false;
	@Column
	@Builder.Default()
	private Boolean isCredentialNonExpired = false;
	@Column
	@Builder.Default()
	private Boolean isEnabled = false;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
