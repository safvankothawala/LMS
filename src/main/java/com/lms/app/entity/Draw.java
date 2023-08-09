package com.lms.app.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class for Draw
 */
@Entity
@Table(name = "DRAW")
public class Draw {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DRAWID")
	private Long drawID;

	@Column(name = "DRAWNUMBER", length = 50, unique = true, nullable = false)
	private String drawNumber;

	@CreationTimestamp
	@Column(name = "DATEOFCREATION", nullable = false)
	private Timestamp dateOfCreation;

	@UpdateTimestamp
	@Column(name = "LASTMODIFIEDDATE", nullable = false)
	private Timestamp lastModifiedDate;

	@Column(name = "STARTDATE", nullable = false)
	private Timestamp startDate;

	@Column(name = "ENDDATE", nullable = false)
	private Timestamp endDate;

	@Column(name = "MAXTICKETS", nullable = false)
	private Long maxTickets;

	public Draw() {
	}

	public Draw(String drawNumber, Timestamp dateOfCreation, Timestamp lastModifiedDate, Timestamp startDate,
			Timestamp endDate, Long maxTickets) {
		this.drawNumber = drawNumber;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedDate = lastModifiedDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxTickets = maxTickets;
	}

	public Long getDrawID() {
		return drawID;
	}

	public String getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
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

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Long getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(Long maxTickets) {
		this.maxTickets = maxTickets;
	}

	@Override
	public String toString() {
		return "Draw [drawID=" + drawID + ", drawNumber=" + drawNumber + ", dateOfCreation=" + dateOfCreation
				+ ", lastModifiedDate=" + lastModifiedDate + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", maxTickets=" + maxTickets + "]";
	}

}
