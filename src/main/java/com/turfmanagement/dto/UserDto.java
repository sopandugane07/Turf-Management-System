package com.turfmanagement.dto;

import org.springframework.beans.BeanUtils;

import com.turfmanagement.entity.User;

public class UserDto {
	
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
	
	private int hotelId;

	
	public static UserLoginResponse toUserLoginResponse(User user) {
		UserLoginResponse userLoginResponse=new UserLoginResponse();
		BeanUtils.copyProperties(user, userLoginResponse, "password");		
		return userLoginResponse;
	}

}
