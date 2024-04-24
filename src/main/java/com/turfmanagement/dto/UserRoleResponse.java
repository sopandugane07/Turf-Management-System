package com.turfmanagement.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserRoleResponse extends CommonApiResponse {

	private List<String> roles;
	
	private List<String> genders;
	
}
