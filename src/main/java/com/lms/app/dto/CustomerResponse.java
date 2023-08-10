package com.lms.app.dto;

/**
 * DTO for Customer
 */
public class CustomerResponse extends ResponseDTO {

	private long customerID;

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

}
