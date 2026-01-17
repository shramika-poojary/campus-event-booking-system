package com.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Booking;
import com.data.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	List<Payment> findByBookingBookingId(int bookingId);

	Optional<Payment> findByRazorPayOrderId(String razorPayOrderId);
}
