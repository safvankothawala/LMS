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
 * Entity class for TicketOwner
 */
@Entity
@Table(name = "TICKETOWNER")
public class TicketOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKETOWNERID")
	private Long ticketOwnerID;

	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@UpdateTimestamp
	@Column(name = "LASTMODIFIEDDATE", nullable = false)
	private Timestamp lastModifiedDate;

	@Column(name = "MOBILENUMBER", nullable = false, length = 30)
	private String mobileNumber;

	@Column(name = "TICKETOWNERIDENTITY", nullable = false, length = 100, unique = true)
	private String ticketOwnerIdentity;

	@Column(name = "PAYMENTMETHOD", nullable = false, length = 100)
	private String paymentMethod;

	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "CUSTOMERID")
	@ManyToOne(optional = true)
	private Customer customer;

	public TicketOwner() {
	}

	public TicketOwner(String name, Timestamp dateOfCreation, Timestamp lastModifiedDate, String mobileNumber,
			String ticketOwnerIdentity, String paymentMethod, Customer customer) {
		this.name = name;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
		this.mobileNumber = mobileNumber;
		this.ticketOwnerIdentity = ticketOwnerIdentity;
		this.paymentMethod = paymentMethod;
		this.customer = customer;
	}

	public Long getTicketOwnerID() {
		return ticketOwnerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTicketOwnerIdentity() {
		return ticketOwnerIdentity;
	}

	public void setTicketOwnerIdentity(String ticketOwnerIdentity) {
		this.ticketOwnerIdentity = ticketOwnerIdentity;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
