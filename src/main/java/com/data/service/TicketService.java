package com.data.service;

import java.util.List;
import java.util.Optional;

import com.data.dto.TicketResponseDTO;
import com.data.model.Ticket;

public interface TicketService {

	List<Ticket> getTicketsByUser(int userId);
	TicketResponseDTO getTicketByBookingId(int BookingId);
}
