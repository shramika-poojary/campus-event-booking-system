package com.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	Optional<Booking> findByUserUserIdAndEventEventId(Long userId, Integer eventId);
	List<Booking> findByUserUserId(Long userId);
	List<Booking> findByEventEventId(Integer eventId);
}
