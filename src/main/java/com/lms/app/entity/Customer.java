package com.lms.app.entity;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity class for Customer
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUSTOMERID")
	private Long customerID;

	@Column(name = "CUSTOMERNAME", nullable = false, length = 50)
	private String customerName;

	@Column(name = "CUSTOMERIDENTITY", nullable = false, length = 100, unique = true)
	private String customerIdentity;

	@Column(name = "PAYMENTMETHOD", nullable = false, length = 100)
	private String paymentMethod;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@UpdateTimestamp
	@Column(name = "LASTMODIFIEDDATE", nullable = false)
	private Timestamp lastModifiedDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "LICENSEID", referencedColumnName = "LICENSEID")
	private License license;

	@OneToMany(mappedBy = "customer")
	private List<TicketAssociation> tickets;

	public Customer() {
	}

	public Customer(String customerName, String customerIdentity, String paymentMethod, Timestamp dateOfCreation,
			Timestamp lastModifiedDate, License license, List<TicketAssociation> tickets) {
		this.customerName = customerName;
		this.customerIdentity = customerIdentity;
		this.paymentMethod = paymentMethod;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
		this.license = license;
		this.tickets = tickets;
	}

	public List<TicketAssociation> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketAssociation> tickets) {
		this.tickets = tickets;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerIdentity() {
		return customerIdentity;
	}

	public void setCustomerIdentity(String customerIdentity) {
		this.customerIdentity = customerIdentity;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
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

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

}
