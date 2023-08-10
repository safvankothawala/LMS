package com.lms.app.dto;

/**
 * Base class for DTO
 */
public class ResponseDTO {

	private long responseCode = 0;
	private String responseMessage = "SUCCESS";

	public long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
