package com.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.dto.LoginRequest;
import com.data.exception.InvalidCredentialsException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.User;
import com.data.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true"
	)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserServiceImpl userService; 
	@Autowired
	private AuthenticationManager authenticationManager;


	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user){
		System.out.println("REGISTER API HIT");
		User savedUser = userService.registerUser(user);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,HttpServletRequest httpRequest) {

	    Authentication authentication =
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            request.getEmail(),
	                            request.getPassword()
	                    )
	            );
	    
	 
	    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	    securityContext.setAuthentication(authentication);

	    httpRequest.getSession(true)
	        .setAttribute(
	            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
	            securityContext
	        );
	    return ResponseEntity.ok(authentication);
	}
	
	

	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		request.getSession().invalidate();
	    
		return ResponseEntity.ok("Logout Successful");
	}
	
	
}
