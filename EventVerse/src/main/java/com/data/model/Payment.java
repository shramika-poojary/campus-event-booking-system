package com.data.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="Payments")
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int paymentId;
	
	private String razorPayOrderId;
	
	private String razorPayPaymentId;
	
	private String razorPaySignature;

	@NonNull
	private Float amount;
	
	@NonNull
	private String status;  // CREATED / SUCCESS / FAILED
	@CreatedDate
	private LocalDateTime paymentDate;
	
	@ManyToOne
	@JoinColumn(name = "bookingId")
	private Booking booking;
	
}

