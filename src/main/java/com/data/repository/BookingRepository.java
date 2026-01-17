package com.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Booking;
import com.data.model.Ticket;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	Optional<Booking> findByUserUserIdAndEventEventId(int userId, int eventId);
	List<Booking> findByUserUserId(int userId);
	List<Booking> findByEventEventId(int eventId);
	
	
}
