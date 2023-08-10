package com.lms.app.dto;

/**
 * DTO for Draw WinnerResponse
 */
public class DrawWinnerResponse extends ResponseDTO {

	private long ticketAssociationID;
	private String ticketNumber;
	private String customerIdentity;
	private String ticketOwnerIdentity;

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public long getTicketAssociationID() {
		return ticketAssociationID;
	}

	public void setTicketAssociationID(long ticketAssociationID) {
		this.ticketAssociationID = ticketAssociationID;
	}

	public String getCustomerIdentity() {
		return customerIdentity;
	}

	public void setCustomerIdentity(String customerIdentity) {
		this.customerIdentity = customerIdentity;
	}

	public String getTicketOwnerIdentity() {
		return ticketOwnerIdentity;
	}

	public void setTicketOwnerIdentity(String ticketOwnerIdentity) {
		this.ticketOwnerIdentity = ticketOwnerIdentity;
	}

}
