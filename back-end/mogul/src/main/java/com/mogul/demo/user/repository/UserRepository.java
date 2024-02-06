package com.mogul.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mogul.demo.user.entity.User;

import jakarta.annotation.Nonnull;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//save()는 user 존재 여부에 따라 CREATE 또는 UPDATE

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	// @Nonnull
	// Optional<User> findById(@Nonnull Long aLong);


	@Nonnull
	Optional<User> findByEmail(String email);
}
