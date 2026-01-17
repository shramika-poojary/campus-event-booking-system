package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.data.enums.Role;
import com.data.exception.InvalidCredentialsException;
import com.data.exception.ListIsEmptyException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.User;
import com.data.repository.BookingRepository;
import com.data.repository.PaymentRepository;
import com.data.repository.TicketRepository;
import com.data.repository.UserRepository;

import lombok.Data;

@Service
@Data
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Register user
	@Override
	public User registerUser(User user) {
		
		// Role-based validation
        if (user.getRole()==Role.USER) {

            if (user.getStream() == null || user.getYear() == null) {
                throw new RuntimeException("Student must have stream and year");
            }

        } else if (user.getRole()==Role.ADMIN) {

           
            user.setStream(null);
            user.setYear(null);
            user.setBookings(null);
        }
		
		user.setPassword(passwordEncoder.encode(user.getPassword())); //hash password
		User u= repo.save(user);
		if(u!=null) {
			return u;
		}else {
			throw new NullPointerException("Registration failed");
		}
		
	}

	

	@Override
	public User getUserById(int userId) {
		Optional<User> user=repo.findById(userId);
		if(!user.isEmpty()) {
			return user.get();
		}else {
			throw new ResourceNotFoundException("User Not Found");//handle exception
		}

	}
	


	@Override
	public List<User> getAllUsers() {
		List<User> user=repo.findAll();
		if(!user.isEmpty()) {
			return user;
		}else {
			throw new ListIsEmptyException("List is Empty");  //handle exception
		}
	
	}

	@Override
	public boolean deleteUser(int userId) {
		Optional<User> u= repo.findById(userId);
		if(u.isPresent()) {
			repo.deleteById(userId);
			return true;
		}else {
			throw new ResourceNotFoundException("Resource Not Found");
		}
		 
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> user=repo.findByEmail(email);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("User not found with email:"+email);  //handle exception
	    }else{
	    	return user.get();
	    }
		
	}

	@Override
	public User updateUserByUserId(User user, int userId) {
		Optional<User> id = repo.findById(userId);
		if(!id.isPresent()) {
			throw new ResourceNotFoundException("User not found"); 
		}else {
			User existingUser = id.get();
			existingUser.setName(user.getName());
			existingUser.setContact(user.getContact());
			existingUser.setCollegeName(user.getCollegeName());
			existingUser.setStream(user.getStream());
			existingUser.setYear(user.getYear());
			
			return repo.save(existingUser);
		}
		
	}
	
}
