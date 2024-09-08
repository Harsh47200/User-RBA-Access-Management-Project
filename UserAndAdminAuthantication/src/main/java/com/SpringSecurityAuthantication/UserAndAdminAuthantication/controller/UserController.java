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

	// Automatically inject an instance of AuthenticationManager
		@Autowired
		private AuthenticationManager authenticationManager;
		
		// Automatically inject an instance of JwtUtil
		@Autowired
		private JwtUtil jwtUtil;
		
		// Automatically inject an instance of LoginServices
		@Autowired
		private UserService userService;
		
		// Automatically inject an instance of BCryptPasswordEncoder
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;
		
		// Automatically inject an instance of UserRepositry
		@Autowired
		private UserRepository userRepository;
		
		// Define an endpoint for login (GET request to "/api/login")
		@GetMapping("/login")
		public ResponseEntity<?> login(){
			
			// Create a new instance of LoginDto
			LoginDto loginDto = new LoginDto();
			
			// Return a successful response with the loginDto object
			return ResponseEntity.ok(new GenricResponse(201, "Success", loginDto));
		}
		
		// Define an endpoint for signup (POST request to "/api/signup")
		@PostMapping("/signup")
		public ResponseEntity<?> signup(@RequestBody User user){
			
			// Encode the user's password using BCryptPasswordEncoder
			String password = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(password); // Set the encoded password
			user.setRole("ADMIN");
			
			// Call the addUserDetails method from LoginServices to save the user and return the response
			return userService.addUserDetails(user);
		}
		
		// Define an endpoint for authentication (GET request to "/api/auth")
		@GetMapping("/auth")
		public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) throws Exception {
			
			// Authenticate the user using the provided email and password
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
			);
			
			// Check if the authentication was successful
			if (authentication.isAuthenticated()) {
				 
				// Create a new instance of TokenResponse
				TokenResponse tokenResponse = new TokenResponse();
				 
				// Retrieve the User object by email
				User user = userRepository.findByEmail(loginDto.getEmail());
				 
				// Set the user and generated token in the tokenResponse object
				tokenResponse.setUser(user);
				tokenResponse.setToken(jwtUtil.generateToken(loginDto.getEmail()));
				 
				// Return a successful response with the tokenResponse object
				return ResponseEntity.ok(new GenricResponse(201, "Success", tokenResponse));
			} else {
				// Throw an exception if authentication fails
				throw new UsernameNotFoundException("Invalid user request!");
			} 
		}
		
		// Define an endpoint to check the current user's authentication (GET request to "/api/check")
		@GetMapping("/check")
		public ResponseEntity<?> check() {
			
			// Get the current authentication object from the SecurityContext
			Authentication user = SecurityContextHolder.getContext().getAuthentication();
			
			// Retrieve the UserDetailsInfo object from the authentication principal
			UserDetailsInfo user1 = (UserDetailsInfo) user.getPrincipal();
			
			// Create a new instance of UserDetails and populate it with user information
			UserDetails userDetails = new UserDetails();
			userDetails.setId(user1.getUser().getId());
			userDetails.setEmail(user1.getUser().getEmail());
			
			// Return a successful response with the userDetails object
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
		    // Retrieve the current authenticated user's role
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    UserDetailsInfo userDetails = (UserDetailsInfo) authentication.getPrincipal();
		    String userRole = userDetails.getUser().getRole();

		    // Check if the current user has an admin role
		    if ("ADMIN".equalsIgnoreCase(userRole)) {
		        // Call the deleteUserDetails method from UserService to delete the user
		        return userService.deleteUserDetails(id, userDetails.getUser());
		    } else {
		        // Return a forbidden response if the user does not have an admin role
		        return ResponseEntity.status(403).body(new GenricResponse(403, "Access denied", null));
		    }
		}

		
}
