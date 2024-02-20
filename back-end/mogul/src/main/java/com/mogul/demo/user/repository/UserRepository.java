package com.mogul.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mogul.demo.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User save(User user);
	Optional<User> findByUsername(String username);
	Optional<User> findById(Long id);
	Boolean existsUserByUsername(String username);
	Boolean existsUserByNickname(String nickname);

}