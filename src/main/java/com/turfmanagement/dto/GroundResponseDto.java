package com.turfmanagement.dto;

import java.util.List;

import com.turfmanagement.entity.Ground;

import lombok.Data;

@Data
public class GroundResponseDto extends CommonApiResponse {
	
	private List<Ground> grounds;

}
