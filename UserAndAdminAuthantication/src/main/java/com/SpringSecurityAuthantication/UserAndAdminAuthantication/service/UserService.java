package com.SpringSecurityAuthantication.UserAndAdminAuthantication.service;

import org.springframework.http.ResponseEntity;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;

public interface UserService {
	ResponseEntity<?> addUserDetails(User user);

	ResponseEntity<?> getUserDetails();
	
	   ResponseEntity<?> deleteUserDetails(String id, User user);
	}
