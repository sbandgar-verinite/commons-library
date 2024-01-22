package com.verinite.commons.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.verinite.commons.model.User;

public interface UserService {

	UserDetails loadUserByUsername(String email);

	User findByEmail(String userEmail);

}
