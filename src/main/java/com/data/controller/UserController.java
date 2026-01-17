package com.data.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.dto.PaymentRequest;
import com.data.dto.TicketResponseDTO;
import com.data.model.Booking;
import com.data.model.Event;
import com.data.model.Payment;
import com.data.model.Ticket;
import com.data.model.User;
import com.data.service.BookingServiceImpl;
import com.data.service.PaymentServiceImpl;
import com.data.service.TicketServiceImpl;
import com.data.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true"
	)
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("api/user")
public class UserController {

	
	@Autowired
	private UserServiceImpl service;
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private TicketServiceImpl ticketService;
	
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> view_profile(Authentication authentication) {
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = service.getUserByEmail(email);
	    return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	//update own profile
	@PutMapping("/update/{userId}")
	public ResponseEntity<?> update_Profile(@PathVariable int userId,@RequestBody User user){
		User updated=service.updateUserByUserId(user, userId);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	}
	
	
	//Book event
	@PostMapping("/bookevent/{eventId}")
	public ResponseEntity<?> book_event(Authentication authentication,@PathVariable int eventId){
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = service.getUserByEmail(email);
	    
	    Booking booking = bookingService.bookEvent(user.getUserId(), eventId);
	    return new ResponseEntity<>(booking, HttpStatus.CREATED);
	    
	}
	
	//paynow
	@PostMapping("/payment/create/{eventId}")
	public ResponseEntity<?> create_Payment(Authentication authentication,@PathVariable int eventId){
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = service.getUserByEmail(email);
	    
	    Payment payment = paymentService.createPayment(user.getUserId(),eventId);
	    return new ResponseEntity<>(payment, HttpStatus.CREATED);
		
	}
	
	// Payment success
	@PostMapping("/payment/verify")
	 public ResponseEntity<?> verifyPayment(@RequestBody PaymentRequest request) {
		
		System.out.println("Payment ID: " + request.getPaymentId());
	    System.out.println("Status received: " + request.getStatus());

		Payment payment =paymentService.updatePaymentStatus(request.getPaymentId(),request.getStatus());
		
		 Map<String, Object> response = new HashMap<>();
		    response.put("paymentId", payment.getPaymentId());
		    response.put("status", payment.getStatus());
		    response.put("bookingId", payment.getBooking().getBookingId());

		    return ResponseEntity.ok(response);

	}
		
	@GetMapping("/bookings")
	public ResponseEntity<?> viewOwnBookings(Authentication authentication){
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = service.getUserByEmail(email);
	    
	    List<Booking> bookings = bookingService.getBookingByUser(user.getUserId());
	    return new ResponseEntity<>(bookings, HttpStatus.OK);
		
	}
	
	@GetMapping("/tickets")
	public ResponseEntity<?> viewOwnTickets(Authentication authentication){
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = service.getUserByEmail(email);
	    
	    List<Ticket> tickets = ticketService.getTicketsByUser(user.getUserId());
	    List<Map<String, Object>> response = new ArrayList<>();
	    for (Ticket ticket : tickets) {
	        Booking booking = ticket.getBooking();
	        Event event = booking.getEvent();

	        Map<String, Object> map = new HashMap<>();
	        map.put("ticket", ticket);
	        map.put("booking", booking);
	        map.put("event", event);

	        response.add(map);
	    }


	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get/ticketbybooking/{bookingId}")
	public ResponseEntity<?> getTicketByBookingId(@PathVariable int bookingId){
		TicketResponseDTO ticket=ticketService.getTicketByBookingId(bookingId);
		Booking booking=bookingService.getBookingById(bookingId);
		Event event=booking.getEvent();
		Map<String, Object> response = new HashMap<>();
	    response.put("ticket", ticket);
	    response.put("booking", booking);
	    response.put("event", event);
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
