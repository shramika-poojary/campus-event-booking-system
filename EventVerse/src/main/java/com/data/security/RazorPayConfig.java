package com.data.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RazorPayConfig {

	@Value("${razorpay.payment.key}")
	private String key;
	
	
	@Value("${razorypay.payment.secret}")
    private String secret;


	public String getKey() {
		return key;
	}


	public String getSecret() {
		return secret;
	}


	
	
	
}
