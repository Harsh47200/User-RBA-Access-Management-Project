package com.SpringSecurityAuthantication.UserAndAdminAuthantication.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.UserRepository;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.service.UserService;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.util.GenricResponse;




//@Service class as a Spring service for automatic detection and registration.
@Service
public class UserServiceImpl implements UserService {
	
	  @Autowired
	    private UserRepository userRepository;
	    // Automatically injects an instance of UserRepositry into this class

	 
	    @Override
	    public ResponseEntity<?> addUserDetails(User user) {
	        // Implements the addUserDetails method from the LoginServices interface
	    	
	    	Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
			String email1 = authenticationToken.getName();
			
			User user2 =  userRepository.findByEmail(email1);
			
			
			
	        if(user2!= null) {
	        	System.out.println(user2.getRole());
	        		if(user2.getRole().equals("ADMIN")) {
	        			
	        			String email = user.getEmail();
	      		      
	    		        User user1 = userRepository.findByEmail(email);

	    		        
	    		        if (user1 == null) {
	    		           
	    		            userRepository.save(user);

	    		           
	    		            return ResponseEntity.ok(new GenricResponse(201, "Success", user));
	    		        } else {
	    		            // If the user already exists, return a response indicating that the email is already in use
	    		            return ResponseEntity.ok(new GenricResponse(203, "Sorry Email id already exist", null));
	    		        }
	        			
	        		}else {
	        			 return ResponseEntity.ok(new GenricResponse(403, "UNAUTORIZED", null));
	        		}
		        
	        }else {
	        	
	        	

		        String email = user.getEmail();
		      
		        User user1 = userRepository.findByEmail(email);

		        
		        if (user1 == null) {
		           
		            userRepository.save(user);

		           
		            return ResponseEntity.ok(new GenricResponse(201, "Success", user));
		        } else {
		            // If the user already exists, return a response indicating that the email is already in use
		            return ResponseEntity.ok(new GenricResponse(203, "Sorry Email id already exist", null));
		        }
	        }
	    }


	
		@Override
		public ResponseEntity<?> getUserDetails() {
			// TODO Auto-generated method stub
			
			Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
			String email = authenticationToken.getName();
			
			User user =  userRepository.findByEmail(email);
			
			if(user.getRole().equals("USER")) {
				List<User> user1 =  userRepository.findByRole("USER");
				return  ResponseEntity.ok(new GenricResponse(201, "Success", user1));
			}else {
				 return ResponseEntity.ok(new GenricResponse(403, "UNAUTORIZED", null));
			}
			
			
		}



		@Override
		public ResponseEntity<?> deleteUserDetails(String id,User user) {
			// Check if the user exists
	        if (userRepository.existsById(id)) {
	            userRepository.deleteById(id);
	            return ResponseEntity.ok(new GenricResponse(200, "User deleted successfully", null));
	        } else {
	            return ResponseEntity.status(404).body(new GenricResponse(404, "User not found", null));
	        }
		}

	
}
