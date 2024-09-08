package com.SpringSecurityAuthantication.UserAndAdminAuthantication.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.dto.LoginDto;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.dto.TokenResponse;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.dto.UserDetails;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.UserRepository;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.service.UserService;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.util.GenricResponse;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.util.JwtUtil;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.util.UserDetailsInfo;

@RestController
@RequestMapping("/api")
public class UserController {

	
		@Autowired
		private AuthenticationManager authenticationManager;
		
		
		@Autowired
		private JwtUtil jwtUtil;
		
		
		@Autowired
		private UserService userService;
		
		
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		
		@Autowired
		private UserRepository userRepository;
		
	
		@GetMapping("/login")
		public ResponseEntity<?> login(){
			
			
			LoginDto loginDto = new LoginDto();
			
			
			return ResponseEntity.ok(new GenricResponse(201, "Success", loginDto));
		}
		
		
		@PostMapping("/signup")
		public ResponseEntity<?> signup(@RequestBody User user){
			
			
			String password = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(password); // Set the encoded password
			user.setRole("ADMIN");
			
		
			return userService.addUserDetails(user);
		}
		
		
		@GetMapping("/auth")
		public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) throws Exception {
			
			
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
			);
			
			
			if (authentication.isAuthenticated()) {
				 
				
				TokenResponse tokenResponse = new TokenResponse();
				 
				
				User user = userRepository.findByEmail(loginDto.getEmail());
				 
				
				tokenResponse.setUser(user);
				tokenResponse.setToken(jwtUtil.generateToken(loginDto.getEmail()));
				 
				
				return ResponseEntity.ok(new GenricResponse(201, "Success", tokenResponse));
			} else {
				
				throw new UsernameNotFoundException("Invalid user request!");
			} 
		}
		
		
		@GetMapping("/check")
		public ResponseEntity<?> check() {
			
			
			Authentication user = SecurityContextHolder.getContext().getAuthentication();
			
			
			UserDetailsInfo user1 = (UserDetailsInfo) user.getPrincipal();
			
			
			UserDetails userDetails = new UserDetails();
			userDetails.setId(user1.getUser().getId());
			userDetails.setEmail(user1.getUser().getEmail());
			
			
			return ResponseEntity.ok(new GenricResponse(201, "Success", userDetails));
		}
		
		@PostMapping("/addUser")
		public ResponseEntity<?> addUser(@RequestBody User user){
			String password = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(password); // Set the encoded password
			user.setRole("USER");
			
			return userService.addUserDetails(user);
			
		}
		
		@GetMapping("/getUser")
		public ResponseEntity<?> getUser(){
			
			return userService.getUserDetails();
		}
		
		@DeleteMapping("/deleteUser/{id}")
		public ResponseEntity<?> deleteUser(@PathVariable String id) {
		  
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    UserDetailsInfo userDetails = (UserDetailsInfo) authentication.getPrincipal();
		    String userRole = userDetails.getUser().getRole();

		 
		    if ("ADMIN".equalsIgnoreCase(userRole)) {
		    
		        return userService.deleteUserDetails(id, userDetails.getUser());
		    } else {
		      
		        return ResponseEntity.status(403).body(new GenricResponse(403, "Access denied", null));
		    }
		}

		
}
