package com.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.enums.BookingStatus;
import com.data.enums.TicketStatus;
import com.data.exception.EventAlreadyBookedException;
import com.data.exception.ListIsEmptyException;
import com.data.exception.NoSeatsAvailableException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.Booking;
import com.data.model.Event;
import com.data.model.Payment;
import com.data.model.Ticket;
import com.data.model.User;
import com.data.repository.BookingRepository;
import com.data.repository.PaymentRepository;
import com.data.repository.TicketRepository;

import lombok.Data;

@Service
@Data
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingRepository repo;
    @Autowired
    private TicketRepository ticketRepo;
    
    @Autowired
    private PaymentRepository paymentRepo;
    
	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;
	

	@Override
	public Booking bookEvent(int userId, int eventId) {
		
		//Check user
		User user = userService.getUserById(userId);
		
		//Check event
		Event event = eventService.getEventById(eventId);
		
		//Check if user already booked this event
		Optional<Booking> existingBooking = repo.findByUserUserIdAndEventEventId(userId, eventId);
		if(existingBooking.isPresent()) {
			if(existingBooking.get().getBookingStatus()==BookingStatus.BOOKED) {
			throw new EventAlreadyBookedException("User already booked ticket for this event");
			}
		}
		
		//Check seat availability
		if(event.getAvailableSeats()<=0) {
			throw new NoSeatsAvailableException("No seats available for this event");
		}
		
		//Create booking;
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setEvent(event);
		booking.setBookingStatus(BookingStatus.PENDING);  //pending/booked/failed this will change after payment  
		
		Booking savedBooking = repo.save(booking);

        return savedBooking;
        
	}

	@Override
	public Booking confirmBookingAfterPayment(int bookingId) {
		
		Optional<Booking> booking=repo.findById(bookingId);
		if(booking.isEmpty()) {
			throw new ResourceNotFoundException("Booking not found");
		}
		
		//if payment success confirm booking
        Booking existingBooking=booking.get();
        existingBooking.setBookingStatus(BookingStatus.BOOKED);
        
     // Reduce available seats by 1
        Event event = existingBooking.getEvent();
        if (event.getAvailableSeats() <= 0) {
            throw new NoSeatsAvailableException("No seats available");
        }
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventService.save(event);
        
        //ticket generation
        Ticket ticket = new Ticket();
        ticket.setBooking(existingBooking);  //from ticket side
        ticket.setStatus(TicketStatus.VALID);
        ticket.setTicketCode(UUID.randomUUID().toString());

        ticketRepo.save(ticket);
        existingBooking.setTicket(ticket);  //from booking side
		return repo.save(existingBooking);
		
	}
	
	
	
	@Override
	public List<Booking> getBookingByUser(int userId) {
		List<Booking> booking=repo.findByUserUserId(userId);
		if(booking.isEmpty()) {
			throw new ListIsEmptyException("No bookings found by user");
		}else {
			return booking;
		}
	}

	@Override
	public List<Booking> getAllBookings() {
		List<Booking> bookings=repo.findAll();
		if(bookings.isEmpty()) {
			throw new ListIsEmptyException("No bookings available");
		}else {
			return bookings;
		}
	}

	@Override
	public Booking getBookingById(int bookingId) {
		Optional<Booking> booking=repo.findById(bookingId);
		if(booking.isEmpty()) {
			throw new ListIsEmptyException("Booking not found");
		}else {
			return booking.get();
		}
	}

	@Override
	public List<Booking> getBookingsByEvent(int eventId) {
		List<Booking> bookings=repo.findByEventEventId(eventId);
		if(bookings.isEmpty()) {
			throw new ListIsEmptyException("No bookings available for event");
		}else {
			return bookings;
		}
	}

	@Override
	public boolean validateTicket(String ticketCode) {
		Optional<Ticket> ticket = ticketRepo.findByTicketCode(ticketCode);
		if(ticket==null) {
			throw new ResourceNotFoundException("Invalid Ticket");
		}else {
			 Ticket ticket1=ticket.get();
			 if (ticket1.getStatus().equals("USED")) {
			        throw new RuntimeException("Ticket already used");
			 }
			 else {
				 ticket1.setStatus(TicketStatus.USED);
				  ticketRepo.save(ticket1);
			 }
		}
		return true;
	}


	

	
}
