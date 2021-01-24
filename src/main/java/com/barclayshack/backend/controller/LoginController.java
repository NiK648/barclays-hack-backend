package com.barclayshack.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barclayshack.backend.beans.User;
import com.barclayshack.backend.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		return service.register(user);
	}

	@PostMapping("/login")
	public User login(@RequestBody User user) {
		return service.login(user);
	}

}
