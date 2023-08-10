package com.lms.app.dto;

/**
 * DTO for TicketOwner
 */
public class TicketOwnerResponse extends ResponseDTO {

	private long ticketOwnerID;

	public long getTicketOwnerID() {
		return ticketOwnerID;
	}

	public void setTicketOwnerID(long ticketOwnerID) {
		this.ticketOwnerID = ticketOwnerID;
	}

}
