package com.mogul.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mogul.demo.user.entity.User;

import jakarta.annotation.Nonnull;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User save(User entity); //존재 여부에 따라 CREATE 또는 UPDATE

	@Nonnull
	Optional<User> findById(@Nonnull Long aLong);

	@Nonnull
	Optional<User> findByEmail(String email);
}
