package com.lms.app.dto;

/**
 * DTO for Generate Tickets for Draw
 */
public class GenerateTicketsResponse extends ResponseDTO {

	private long ticketsGenerated;

	public long getTicketsGenerated() {
		return ticketsGenerated;
	}

	public void setTicketsGenerated(long ticketsGenerated) {
		this.ticketsGenerated = ticketsGenerated;
	}

}
