package com.barclayshack.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barclayshack.backend.adapter.LoginAdapter;
import com.barclayshack.backend.beans.User;

@Service
public class LoginService {

	@Autowired
	private LoginAdapter adapter;

	public String register(User user) {
		return adapter.register(user);
	}

	public User login(User user) {
		return adapter.login(user);
	}

}
