package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
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
			throw new EventAlreadyBookedException("User already booked ticket for this event");
		}
		
		//Check seat availability
		if(event.getAvailableSeats()<=0) {
			throw new NoSeatsAvailableException("No seats available for this event");
		}
		
		//Create booking;
		Booking booking = new Booking();
		booking.setUser(user);
		booking.setEvent(event);
		booking.setBookingStatus("PENDING");  //pending/booked/failed
		
		Booking savedBooking = repo.save(booking);
		
		//Payment process
		Payment payment = new Payment();
        payment.setBooking(savedBooking);
        payment.setAmount(event.getPrice());
        payment.setStatus("SUCCESS");
        paymentRepo.save(payment);
		
        //if payment success confirm booking
        savedBooking.setBookingStatus("BOOKED");
        repo.save(savedBooking);
        
        
		// Reduce available seats by 1
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventService.save(event);
        
        //ticket generation
        Ticket ticket = new Ticket();
        ticket.setBooking(savedBooking);
        ticket.setStatus("VALID");
        ticket.setTicketCode("TKT-" + savedBooking.getBookingId());

        ticketRepo.save(ticket);

        return savedBooking;
        
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
		Ticket ticket = repo.findByTicketCode(ticketCode);
		if(ticket==null) {
			throw new ResourceNotFoundException("Invalid Ticket");
		}else {
			 if (ticket.getStatus().equals("USED")) {
			        throw new RuntimeException("Ticket already used");
			 }
			 else {
				 ticket.setStatus("USED");
				  ticketRepo.save(ticket);
			 }
		}
		return true;
	}

	
}
