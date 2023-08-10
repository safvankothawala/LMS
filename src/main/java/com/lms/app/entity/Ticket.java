package com.lms.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class for Ticket
 */
@Entity
@Table(name = "TICKET")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKETID")
	private Long ticketID;

	@Column(name = "TICKETNUMBER", nullable = false, length = 50, unique = true)
	private String ticketNumber;

	@Column(columnDefinition = "boolean default true")
	private boolean available = true;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@JoinColumn(name = "DRAWID", referencedColumnName = "DRAWID")
	@ManyToOne(optional = true)
	private Draw draw;

	public Ticket() {
	}

	public Ticket(String ticketNumber, boolean available, Timestamp dateOfCreation, Draw draw) {
		super();
		this.ticketNumber = ticketNumber;
		this.available = available;
		this.dateOfCreation = dateOfCreation;
		this.draw = draw;
	}

	public Long getTicketID() {
		return ticketID;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Timestamp getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Timestamp dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
