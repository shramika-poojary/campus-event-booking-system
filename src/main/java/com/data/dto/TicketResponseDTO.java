package com.data.dto;

import java.time.LocalDateTime;

import com.data.enums.TicketStatus;

import lombok.Data;
@Data
public class TicketResponseDTO {

	private int ticketId;
    private String ticketCode;
    private TicketStatus status;
    private LocalDateTime issuedAt;
    private int bookingId;
    private String name; 
}
