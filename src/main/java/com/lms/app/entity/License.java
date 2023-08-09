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
	
	@Column(name = "LICENSEKEY", nullable = false, length = 50)
	private String licenseKey;
	
	@Column(name = "MAXTICKETSPERDRAW", nullable = false)
	private int maxTicketsPerDraw;
	
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

	public License(String licenseKey, int maxTicketsPerDraw, Timestamp validityPeriod, Timestamp dateOfCreation,
			Timestamp lastModifiedDate) {
		this.licenseKey = licenseKey;
		this.maxTicketsPerDraw = maxTicketsPerDraw;
		this.validityPeriod = validityPeriod;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
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

	public int getMaxTicketsPerDraw() {
		return maxTicketsPerDraw;
	}

	public void setMaxTicketsPerDraw(int maxTicketsPerDraw) {
		this.maxTicketsPerDraw = maxTicketsPerDraw;
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

}
