package com.turfmanagement.dto;

import lombok.Data;

@Data
public class UpdateBookingStatusRequestDto {
	
	private int bookingId;
	
	private String status;

}
