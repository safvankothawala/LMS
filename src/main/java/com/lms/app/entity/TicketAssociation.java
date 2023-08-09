package com.lms.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class for TicketAssociation
 */
@Entity
@Table(name = "TICKETASSOCIATION")
public class TicketAssociation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKETASSOCIATIONID")
	private Long ticketAssociationID;

	@Column(columnDefinition = "boolean default false")
	private boolean isWinner;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@UpdateTimestamp
	@Column(name = "LASTMODIFIEDDATE", nullable = false)
	private Timestamp lastModifiedDate;

	@JoinColumn(name = "TICKETNUMBER", referencedColumnName = "TICKETNUMBER")
	@ManyToOne(optional = true)
	private Ticket ticket;

	@JoinColumn(name = "TICKETOWNERIDENTITY", referencedColumnName = "TICKETOWNERIDENTITY")
	@ManyToOne(optional = true)
	private TicketOwner ticketOwner;

	public TicketAssociation() {
	}

	public TicketAssociation(boolean isWinner, Timestamp dateOfCreation, Timestamp lastModifiedDate, Ticket ticket,
			TicketOwner ticketOwner) {
		this.isWinner = isWinner;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
		this.ticket = ticket;
		this.ticketOwner = ticketOwner;
	}

	public Long getTicketAssociationID() {
		return ticketAssociationID;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public Timestamp getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Timestamp dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public TicketOwner getTicketOwner() {
		return ticketOwner;
	}

	public void setTicketOwner(TicketOwner ticketOwner) {
		this.ticketOwner = ticketOwner;
	}

}
