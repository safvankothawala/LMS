package com.lms.app.dto;

/**
 * DTO for Purchase Ticket Response
 */
public class PurchaseTicketResponse extends ResponseDTO {

	private long ticketAssociationID;
	private String ticketNumber;

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

}
