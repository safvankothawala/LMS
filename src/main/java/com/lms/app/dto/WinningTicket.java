package com.lms.app.dto;

/**
 * DTO for Winning Ticket
 */
public class WinningTicket extends ResponseDTO {

	private String ticketOwnerIdentity;
	private String ticketNumber;
	private String drawNumber;

	public String getTicketOwnerIdentity() {
		return ticketOwnerIdentity;
	}

	public void setTicketOwnerIdentity(String ticketOwnerIdentity) {
		this.ticketOwnerIdentity = ticketOwnerIdentity;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}

}
