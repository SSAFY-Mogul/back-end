package com.mogul.demo.user.service;

import java.time.LocalDateTime;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	@Override
	@Transactional
	public User addUser(User user) {
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	@Override
	@Transactional
	public User modifyUser(User user) {
		User modifiedUser = getUser(user.getId());
		modifiedUser.setNickname(user.getNickname()); // 더티체킹
		return modifiedUser;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당하는 유저가 없습니다"));
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException("해당하는 유저가 없습니다"));
		return user;
	}



	@Override
	@Transactional
	public Boolean deleteUser(Long id) {
		User deletedUser = getUser(id);
		deletedUser.setDeletedDate(LocalDateTime.now());
		deletedUser.setIsDeleted(true);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public void duplicateNickname(String nickname) {
		Boolean isExist = userRepository.existsUserByNickname(nickname);
		if(isExist) throw new DuplicateKeyException("중복된 닉네임입니다.");
	}

	@Override
	@Transactional(readOnly = true)
	public void duplicateUsername(String username) {
		Boolean isExist = userRepository.existsUserByUsername(username);
		if(isExist) throw new DuplicateKeyException("중복된 이메일입니다.");
	}

	@Override
	public void passwordEqual(String password_input, String password) {
		Boolean isEqual =  passwordEncoder.matches(password_input,password);
		if(!isEqual) throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getPrincipal().toString();
			User user = getUserByUsername(username);
			return user;
		}
		else{
			throw new UsernameNotFoundException("잘못된 요청입니다");
		}
	}
}
