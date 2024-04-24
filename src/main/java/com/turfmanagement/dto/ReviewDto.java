package com.turfmanagement.dto;

public class ReviewDto {

	private String user;
	
	private int star;
	
	private String review;
	
	public ReviewDto(String user, int star, String review) {
		super();
		this.user = user;
		this.star = star;
		this.review = review;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	
	
}
