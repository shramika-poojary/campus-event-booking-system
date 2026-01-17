package com.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	Optional<Ticket> findByBookingBookingId(int bookingId);
	Optional<Ticket> findByTicketCode(String ticketCode);
}
