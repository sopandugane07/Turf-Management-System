package com.turfmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String bookingId;
	
	private String timeSlot;
	
	private String date;
	
	private int userId;
	
	private int groundId;
	
	private String status;

}
