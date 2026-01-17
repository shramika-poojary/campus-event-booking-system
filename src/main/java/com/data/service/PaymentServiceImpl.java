package com.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.enums.PaymentStatus;
import com.data.exception.ListIsEmptyException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.Booking;
import com.data.model.Event;
import com.data.model.Payment;
import com.data.model.User;
import com.data.repository.BookingRepository;
import com.data.repository.PaymentRepository;
import com.data.repository.UserRepository;

import lombok.Data;
import lombok.NonNull;

@Service
@Data
public class PaymentServiceImpl implements PaymentService {

	@Autowired
    private PaymentRepository paymentRepo;
	
	@Autowired
	private UserServiceImpl userService;
	@Autowired
    private BookingService bookingService;
	
	@Autowired
	private EventServiceImpl eventService;
	
	
	@Override  //triggered when clicked on paynow button this will create booking as well with pending status
	public Payment createPayment(int userId,int eventId) {
		User user = userService.getUserById(userId);
	    Event event = eventService.getEventById(eventId);
	    
	    Booking booking = bookingService.bookEvent(userId, eventId);
	    
	    

	    Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getEvent().getPrice());
        payment.setStatus(PaymentStatus.CREATED);
        payment.setRazorPayOrderId(UUID.randomUUID().toString());

	    return paymentRepo.save(payment);
	    
	}


	@Override
	public List<Payment> getAllPayments() {
		List<Payment> payments = paymentRepo.findAll();
        if (payments.isEmpty()) {
            throw new ListIsEmptyException("No payments found");
        }
        return payments;
	}

	@Override
	public List<Payment> getPaymentsByBooking(int bookingId) {
		List<Payment> payments=paymentRepo.findByBookingBookingId(bookingId);
		if (payments.isEmpty()) {
            throw new ListIsEmptyException("No payments found");
        }
        return payments;
	}

	@Override
	public Payment updatePaymentStatus(int paymentId, PaymentStatus paymentStatus) {
		Optional<Payment> optionalPayment =
                paymentRepo.findById(paymentId);

        if (!optionalPayment.isPresent()) {
            throw new ResourceNotFoundException("Payment not found");
        }
        
        Payment payment = optionalPayment.get();
      
        payment.setRazorPayPaymentId("pay" + UUID.randomUUID().toString());
        payment.setRazorPaySignature("dummy_signature");

        
      

        if (paymentStatus == paymentStatus.SUCCESS) {
        	 payment.setStatus(PaymentStatus.SUCCESS);
            bookingService.confirmBookingAfterPayment(
                    payment.getBooking().getBookingId()
            );
        }else {
        	payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentRepo.save(payment);

	}

	
	
}
