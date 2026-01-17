package com.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	List<Payment> findByBookingBookingId(Integer bookingId);
}
