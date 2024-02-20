package com.mogul.demo.user.service;

import com.mogul.demo.user.entity.User;

/**
 * The interface User service.
 */
public interface UserService {

	/**
	 * Add user user.
	 *
	 * @param user the user
	 * @return the user
	 */
	User addUser(User user);

	/**
	 * Modify user user.
	 *
	 * @param user the user
	 * @return the user
	 */
	User modifyUser(User user);

	/**
	 * Gets user.
	 *
	 * @param id the id
	 * @return the user
	 */
	User getUser(Long id);

	/**
	 * Gets user by username.
	 *
	 * @param username the username
	 * @return the user by username
	 */
	User getUserByUsername(String username);

	/**
	 * Gets user by token.
	 * 사용자의 토큰을 기준으로 유저객체를 리턴합니다
	 * @return the user by token
	 */
	User getUserByToken();

	/**
	 * Delete user boolean.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	Boolean deleteUser(Long id);

	/**
	 * Duplicate nickname.
	 *
	 * @param nickname the nickname
	 */
	void duplicateNickname(String nickname);

	/**
	 * Duplicate username.
	 *
	 * @param username the username
	 */
	void duplicateUsername(String username);

	/**
	 * Password equal.
	 *
	 * @param password_input the password input
	 * @param password       the password
	 */
	void passwordEqual(String password_input,String password);
}
