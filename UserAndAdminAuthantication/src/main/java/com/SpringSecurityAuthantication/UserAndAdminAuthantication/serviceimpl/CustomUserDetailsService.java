package com.SpringSecurityAuthantication.UserAndAdminAuthantication.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.UserRepository;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.util.UserDetailsInfo;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Annotation that marks this class as a Spring service component. 
    // It will be automatically detected and managed by the Spring container.
    
    @Autowired
    private UserRepository repository;

    // The @Autowired annotation tells Spring to inject the UserRepository 
    // instance into this class automatically. It allows the service to use 
    // the repository for data access.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
        // The loadUserByUsername method is required by the UserDetailsService interface.
        // This method will be used by Spring Security to fetch user details based on the username.
        
        User user = repository.findByEmail(username);
 
        // Calls the repository's method to find a User entity by its email (username).
        // If the email is not found, this method should ideally handle the case
        // where the user does not exist and throw a UsernameNotFoundException.

        UserDetailsInfo detailsInfo = new UserDetailsInfo(user);

        // Creates an instance of UserDetailsInfo, which is presumably a custom 
        // implementation of the UserDetails interface. It wraps the User entity
        // and provides the necessary details for Spring Security to manage authentication.
        
        return detailsInfo;
        
        // Returns the UserDetailsInfo instance to Spring Security for authentication.
    }
}
