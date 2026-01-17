package com.data.security;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.data.model.User;
import com.data.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Autowired
	private UserRepository userRepo;
	

	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();  //hashing algorithm this method creates a password hashing object
	}
	
	
	@Bean
    public UserDetailsService userDetailsService() {

        return email -> {
           Optional<User> optionalUser = userRepo.findByEmail(email);
            if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
            }
            User user=optionalUser.get();
            System.out.println("ROLE FROM DB = " + user.getRole());
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name()) // USER / ADMIN
                    .build();
        };
    }
	
	
	@Bean
	public AuthenticationManager authenticationManager(
	        HttpSecurity http,
	        PasswordEncoder passwordEncoder,
	        UserDetailsService userDetailsService) throws Exception {

	    AuthenticationManagerBuilder authBuilder =
	            http.getSharedObject(AuthenticationManagerBuilder.class);

	    authBuilder
	        .userDetailsService(userDetailsService)
	        .passwordEncoder(passwordEncoder);

	    return authBuilder.build();
	}


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        	.cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/user/**").hasRole("USER")
                    .anyRequest().permitAll()
            )
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .formLogin(form -> form.disable())
            .logout(logout -> logout
                    .logoutUrl("/api/auth/logout")
                 .logoutSuccessHandler((request, response, authentication) -> {
                       response.setStatus(HttpServletResponse.SC_OK);
              })
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                );
        	
        return http.build();
    }
	
	
	
	}

