package com.data.service;

import java.util.List;

import com.data.enums.PaymentStatus;
import com.data.model.Payment;

import lombok.NonNull;

public interface PaymentService {

	//Payment createPayment(int bookingId, float amount);
	//Payment updatePaymentStatus(
	  //      String razorPayOrderId,
	 //       String razorPayPaymentId,
	 //       String razorPaySignature,
	  //      String status
	  //  );
	 
	List<Payment> getPaymentsByBooking(int bookingId);
	
	List<Payment> getAllPayments(); // admin
	Payment createPayment(int userId, int eventId);

	//Payment updatePaymentStatus(int paymentId, String status);

	Payment updatePaymentStatus(int paymentId, PaymentStatus paymentStatus);
}
