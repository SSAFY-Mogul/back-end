package com.mogul.demo.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mogul.demo.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Override
	<S extends User> S save(S entity); //존재 여부에 따라 CREATE 또는 UPDATE

	@Override
	Optional<User> findById(Long aLong);

	@Override
	<S extends User> List<S> findAll(Example<S> example);


	// soft delete이므로 사용하지 않는다.
	// @Override
	// void deleteById(Long aLong);
}
