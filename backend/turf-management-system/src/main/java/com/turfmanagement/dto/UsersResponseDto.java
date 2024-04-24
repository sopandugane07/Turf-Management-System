package com.turfmanagement.dto;

import java.util.List;

import com.turfmanagement.entity.User;

import lombok.Data;

@Data
public class UsersResponseDto extends CommonApiResponse {
	
	private List<User> users;
	
}
