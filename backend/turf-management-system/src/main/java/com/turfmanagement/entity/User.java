package com.turfmanagement.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;

import com.turfmanagement.dto.UserDto;
import com.turfmanagement.dto.UserLoginResponse;

import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	private String password;
	
	private String role;
	
	private double walletAmount;
	
	public static UserLoginResponse toUserLoginResponse(User user) {
		UserLoginResponse userLoginResponse=new UserLoginResponse();
		BeanUtils.copyProperties(user, userLoginResponse, "password");		
		return userLoginResponse;
	}
	
	public static UserDto toUserDto(User user) {
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(user, userDto, "password");		
		return userDto;
	}

}
