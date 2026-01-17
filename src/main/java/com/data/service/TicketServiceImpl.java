package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dto.TicketResponseDTO;
import com.data.exception.ListIsEmptyException;
import com.data.model.Ticket;
import com.data.repository.TicketRepository;

import lombok.Data;

@Service
@Data
public class TicketServiceImpl implements TicketService{


    @Autowired
    private TicketRepository ticketRepo;
    
	@Override
	public List<Ticket> getTicketsByUser(int userId) {
		 List<Ticket> tickets =
	                ticketRepo.findByBookingUserUserId(userId);

	        if (tickets.isEmpty()) {
	            throw new ListIsEmptyException("No tickets found");
	        }
	        
	        return tickets;
	}

	@Override
	public TicketResponseDTO getTicketByBookingId(int BookingId) {
		Ticket ticket = ticketRepo.findByBookingBookingId(BookingId);
		
		TicketResponseDTO dto = new TicketResponseDTO();
		dto.setTicketId(ticket.getTicketId());
		dto.setTicketCode(ticket.getTicketCode());
	    dto.setStatus(ticket.getStatus());
	    dto.setIssuedAt(ticket.getIssuedAt());
	    dto.setBookingId(ticket.getBooking().getBookingId());
	    dto.setName(ticket.getBooking().getUser().getName());  //remove if noyt working
		return dto;
	}

}
