package com.turfmanagement.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public class CommonApiResponse {

	private String responseMessage;

	private HttpStatus status;

	private boolean isSuccess;

	public CommonApiResponse(String responseMessage, HttpStatus status, boolean isSuccess) {
		super();
		this.responseMessage = responseMessage;
		this.status = status;
		this.isSuccess = isSuccess;
	}
	
	public CommonApiResponse() {
		super();
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
