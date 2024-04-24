package com.turfmanagement.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserLoginResponse {

	private int id;
	
	private String firstName;

	private String lastName;

	private int age;

	private String sex;

	private String emailId;

	private String contact;

	private String street;

	private String city;

	private String pincode;
	
	private String role;
	
	private String jwtToken;
	
	private double walletAmount;

}
