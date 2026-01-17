package com.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.data.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="Bookings",
		   uniqueConstraints = {
		      @UniqueConstraint(columnNames = {"userId", "eventId"})
		   }
		)
public class Booking {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int bookingId; //pk
	
	// only one ticket allowed foe each registered user for particular event
	@Enumerated(EnumType.STRING)
	@NonNull
	private BookingStatus bookingStatus; //Booked/pending
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime bookedAt;
	
	@ManyToOne
	@JoinColumn(name = "userId",nullable = false) //fk
	@JsonIgnore //
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name = "eventId",nullable = false) //fk
	@JsonIgnore //
	private Event event;
	
	//One Booking → Many Payments (One-to-Many)
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<Payment> payments = new ArrayList<>();;
	
	//One booking One ticket 
	@OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
	@JsonManagedReference // 
	private Ticket ticket;
}
