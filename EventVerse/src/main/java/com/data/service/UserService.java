package com.data.service;

import java.util.List;
import java.util.Optional;

import com.data.model.User;

public interface UserService {

	User registerUser(User user);
	
	User loginUser(String email,String password);
	
	User getUserById(int userId);  //profile page and also for admin
	
	User updateUserByUserId(User user,int userId);
	
	List<User> getAllUsers(); // admin
	
	boolean deleteUser(int userId); 
	
	User getUserByEmail(String email);
}
