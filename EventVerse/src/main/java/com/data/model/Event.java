package com.data.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="Events")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int eventId; //pk
	@NonNull
	private String title;
	
	@NonNull
	private String description;
	
	private String rules;
	
	@NonNull
	private LocalDate date;
	@NonNull
	private LocalTime time;
	@NonNull
	private String venue;
	@NonNull
	private Float price;
	@NonNull
	private int totalSeats;
	@NonNull
	private int availableSeats;
	@NonNull
	private String posterImg;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//many events belong to one category
	@ManyToOne
	@JoinColumn(name="categoryId") //fk
	private Category category;
	
	//One event can have many bookings
	@OneToMany(mappedBy="event",cascade=CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<>();;
}
