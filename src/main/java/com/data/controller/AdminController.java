package com.data.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.data.model.Booking;
import com.data.model.Category;
import com.data.model.Event;
import com.data.model.Payment;
import com.data.model.Ticket;
import com.data.model.User;
import com.data.service.BookingServiceImpl;
import com.data.service.CategoryServiceImpl;
import com.data.service.EventServiceImpl;
import com.data.service.PaymentServiceImpl;
import com.data.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true"
	)
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("api/admin")
public class AdminController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@Autowired 
	private EventServiceImpl eventService;
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	
	

    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/profile")
	public ResponseEntity<User> view_profile(Authentication authentication) {
		if (authentication == null) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
		String email = authentication.getName();
	    User user = userService.getUserByEmail(email);
	    return new ResponseEntity<>(user,HttpStatus.OK);
	}

	
	@GetMapping("/get/users")
	public ResponseEntity<?> get_all_users(){
		List<User> users=userService.getAllUsers();
		return new ResponseEntity<>(users,HttpStatus.OK);
		
	}
	
	@PostMapping("/add/category")
	public ResponseEntity<?> add_category(@Valid @RequestBody Category category){
		Category Saved=categoryService.addCategory(category);
		return new ResponseEntity<>(Saved,HttpStatus.CREATED);
	
	}
	
	@PutMapping("/update/category/{categoryId}")
	public ResponseEntity<?> update_category(@PathVariable int categoryId,@RequestBody Category category){
		Category updated=categoryService.updateCategoryById(category, categoryId);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	
	}
	
	@DeleteMapping("/delete/category/{categoryId}")
	public ResponseEntity<String> delete_category(@PathVariable int categoryId){
		categoryService.deleteCategoryById(categoryId);
		return new ResponseEntity<>("Category Deleted Succesffuly",HttpStatus.OK);
	
	}
	
	@PostMapping("/add/event/{categoryId}")
	public ResponseEntity<?> add_event(@Valid @RequestBody Event event,@PathVariable int categoryId){
		Event Saved=eventService.addEvent(event, categoryId);
		return new ResponseEntity<>(Saved,HttpStatus.CREATED);
	}
	
	
	
	
	
	@PutMapping("/update/event/{eventId}")
	public ResponseEntity<?> update_event(@Valid @PathVariable int eventId,@RequestBody Event event){
		Event updated=eventService.updateEventById(event, eventId);
		return new ResponseEntity<>(updated,HttpStatus.OK);
	
	}
	
	@DeleteMapping("/delete/event/{eventId}")
	public ResponseEntity<String> delete_event(@PathVariable int eventId){
		eventService.deleteEventById(eventId);
		return new ResponseEntity<>("Event Deleted Succesffuly",HttpStatus.OK);
	
	}
	
	@GetMapping("/get/bookings")
	public ResponseEntity<?> get_all_bookings(){
		List<Booking> bookings=bookingService.getAllBookings();
		return new ResponseEntity<>(bookings,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/bookings/event/{eventId}")
	public ResponseEntity<?> get_all_bookings_by_event(@PathVariable int eventId){
		List<Booking> bookings=bookingService.getBookingsByEvent(eventId);
		return new ResponseEntity<>(bookings,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/bookings/user/{userId}")
	public ResponseEntity<?> get_all_bookings_by_user(@PathVariable int userId){
		List<Booking> bookings=bookingService.getBookingByUser(userId);
		return new ResponseEntity<>(bookings,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/bookings/{bookingId}")
	public ResponseEntity<?> get_all_bookings_by_id(@PathVariable int bookingId){
		Booking booking=bookingService.getBookingById(bookingId);
		return new ResponseEntity<>(booking,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/ticket/{ticketCode}")
	public ResponseEntity<String> validate_ticket(@PathVariable String ticketCode){
		bookingService.validateTicket(ticketCode);
		return new ResponseEntity<>("Ticket is Valid!!",HttpStatus.OK);
		
	}
	

	@GetMapping("/get/payments")
	public ResponseEntity<?> get_all_payments(){
		List<Payment> payments=paymentService.getAllPayments();
		return new ResponseEntity<>(payments,HttpStatus.OK);
		
	}
	
	//Image upload
	@PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {
			String fileName = file.getOriginalFilename();
			String path = "C:\\Users\\Shramika\\OneDrive\\Desktop\\ITvedant\\EventVerse\\images\\" + fileName;

			file.transferTo(new File(path));

			return "/images/" + file.getOriginalFilename();
		

    }
	
}
