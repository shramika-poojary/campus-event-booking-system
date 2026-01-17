package com.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	Ticket findByBookingBookingId(int bookingId);
	Optional<Ticket> findByTicketCode(String ticketCode);
	List<Ticket> findByBookingUserUserId(int userId);
	
}
