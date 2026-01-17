package com.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="Users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId; //pk
	
	@NonNull
	private String name;

	@Column(nullable=false,unique=true)
	private String email;
	
	@NonNull
	private String password;
	
	@Column(nullable=false,unique=true)
	private String contact;
	
	@NonNull
	private String role;  //admin or student
	
	@NonNull
	private String collegeName;
	
	@NonNull
	private String className;
	
	@NonNull
	private int year;
	
	@NonNull
	private String studentType; //insider or outsider
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	//One user can make many bookings
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<>();;
	
	
}
