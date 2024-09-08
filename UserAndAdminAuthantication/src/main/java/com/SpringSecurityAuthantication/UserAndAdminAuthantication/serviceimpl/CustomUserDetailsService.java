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

  
    
    @Autowired
    private UserRepository repository;

   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
     
        
        User user = repository.findByEmail(username);
 
      

        UserDetailsInfo detailsInfo = new UserDetailsInfo(user);

       
        
        return detailsInfo;
        
        
    }
}
