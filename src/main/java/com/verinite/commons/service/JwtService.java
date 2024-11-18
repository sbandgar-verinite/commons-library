package com.verinite.commons.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUserName(String token);

	String generateToken(UserDetails userDetails);

	boolean isTokenValid(String token, UserDetails userDetails);

	String extractEmail(String token);

	void checkRoleBasedAccess(String userEmail, String requestURI, String method);

}
