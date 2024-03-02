package com.turfmanagement.dto;

import java.util.List;

public class ReviewResponseDto extends CommonApiResponse {
	
	private List<ReviewDto> reviews;

	public List<ReviewDto> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}
	
}
