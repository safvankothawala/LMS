package com.lms.app.dto;

/**
 * DTO for Purchase Ticket Request
 */
public class PurchaseTicketRequest {

	private String drawNumber;
	private String ticketOwnerIdentity;

	public String getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}

	public String getTicketOwnerIdentity() {
		return ticketOwnerIdentity;
	}

	public void setTicketOwnerIdentity(String ticketOwnerIdentity) {
		this.ticketOwnerIdentity = ticketOwnerIdentity;
	}

}
