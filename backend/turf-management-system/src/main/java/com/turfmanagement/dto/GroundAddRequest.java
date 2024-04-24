package com.turfmanagement.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class GroundAddRequest {
	
	private int id;

    private String name;
	
	private String description;
	
	private double width;
	
	private double height;
	
	private double length;
	
	private double price;
	
	private MultipartFile image;	
	

}
