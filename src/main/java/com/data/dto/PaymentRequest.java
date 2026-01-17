package com.data.dto;

import com.data.enums.PaymentStatus;

import lombok.Data;
import lombok.NonNull;
@Data
public class PaymentRequest {
	@NonNull
	private int paymentId;
	@NonNull
    private PaymentStatus status;
    
}
