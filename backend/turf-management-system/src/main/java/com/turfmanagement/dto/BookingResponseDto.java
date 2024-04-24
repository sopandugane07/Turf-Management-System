package com.turfmanagement.dto;

import lombok.Data;

@Data
public class BookingResponseDto {
	
    private int id;
	
	private String bookingId;
	
	private String timeSlot;
	
	private String date;
	
	private int userId;
	
	private int groundId;
	
	private String status;
	
	private String customerName;
	
	private String customerContact;
	
	private String groundName;
	
	private String groundImage;
	
	private String price;
	
}
