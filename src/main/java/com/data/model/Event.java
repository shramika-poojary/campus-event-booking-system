package com.data.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@NotBlank(message = "this field is required")
	private String title;
	
	@NonNull
	@NotBlank(message = "this field is required")
	private String description;
	
	private String rules;
	
	@NonNull
	private LocalDate date;
	@NonNull
	private LocalTime time;
	@NonNull
	@NotBlank(message = "this field is required")
	private String venue;
	@NonNull
	private Float price;
	
	private int totalSeats;
	
	private int availableSeats;
	

	private String posterImg;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//many events belong to one category
	@ManyToOne
	@JoinColumn(name="categoryId") //fk
	@JsonIgnore //
	private Category category;
	
	//One event can have many bookings
	@OneToMany(mappedBy="event",cascade=CascadeType.ALL)
	private List<Booking> bookings = new ArrayList<>();;
}
