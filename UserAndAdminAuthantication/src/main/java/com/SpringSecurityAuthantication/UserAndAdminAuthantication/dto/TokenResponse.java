package com.SpringSecurityAuthantication.UserAndAdminAuthantication.dto;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;

public class TokenResponse {
	
	public User user;
	public String token;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
