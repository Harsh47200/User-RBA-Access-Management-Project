package com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;



//@Repository interface as a Spring-managed data access component.
@Repository
//UserReposiotry Interface for interacting with User entities
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmail(String username);

	List<User> findByRole(String string);
}
