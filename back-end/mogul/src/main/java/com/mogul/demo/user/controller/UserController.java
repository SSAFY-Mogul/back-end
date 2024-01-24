package com.mogul.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/user")
@RestController
// @Tag(name = "User", description = "사용자 기능 API")
public class UserController {
	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("login")
	public void login() {

	}

	@GetMapping("logout")
	public void logout() {
		
	}

	@PostMapping("/join")
	public void join() {

	}

	@DeleteMapping("/leave")
	public void leave() {

	}

	@PatchMapping
	public void updateProfile() {

	}

	@GetMapping
	public void profile() {

	}

}
