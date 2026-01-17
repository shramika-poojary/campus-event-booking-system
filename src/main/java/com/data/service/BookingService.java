package com.data.service;

import java.util.List;

import com.data.model.Booking;

public interface BookingService {

	Booking bookEvent(int userId,int eventId);
	
	Booking confirmBookingAfterPayment(int bookingId);
	
	List<Booking> getBookingByUser(int userId);  //my booking page
	
	List<Booking> getAllBookings();  //admin only
	
	Booking getBookingById(int bookingId);   //for payment page
	
	List<Booking> getBookingsByEvent(int eventId);
	
	boolean validateTicket(String ticketCode);

}
