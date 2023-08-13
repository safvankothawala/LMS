/**
 * 
 */
package com.lms.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity class for License
 */
@Entity
@Table(name = "LICENSE")
public class License {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LICENSEID")
	private Long licenseID;

	@Column(name = "LICENSEKEY", nullable = false, length = 50, unique = true)
	private String licenseKey;

	@Column(name = "MAXTICKETS", nullable = false)
	private int maxTickets;

	@Column(name = "VALIDITYPERIOD", nullable = false)
	private Timestamp validityPeriod;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@UpdateTimestamp
	@Column(name = "LASTMODIFIEDDATE", nullable = false)
	private Timestamp lastModifiedDate;

	@OneToOne(mappedBy = "license")
	private Customer customer;

	public License() {
	}

	public License(String licenseKey, int maxTickets, Timestamp validityPeriod, Timestamp dateOfCreation,
			Timestamp lastModifiedDate, Customer customer) {
		super();
		this.licenseKey = licenseKey;
		this.maxTickets = maxTickets;
		this.validityPeriod = validityPeriod;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
		this.customer = customer;
	}

	public Long getLicenseID() {
		return licenseID;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public int getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Timestamp getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Timestamp validityPeriod) {
		this.validityPeriod = validityPeriod;
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

	@Override
	public String toString() {
		return "License [licenseID=" + licenseID + ", licenseKey=" + licenseKey + ", maxTickets=" + maxTickets
				+ ", validityPeriod=" + validityPeriod + ", dateOfCreation=" + dateOfCreation + ", lastModifiedDate="
				+ lastModifiedDate + ", customer=" + customer + "]";
	}

}
