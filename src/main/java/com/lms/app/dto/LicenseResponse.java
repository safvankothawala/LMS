package com.lms.app.dto;

/**
 * DTO for License
 */
public class LicenseResponse extends ResponseDTO {

	private long licenseID;

	public long getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(long licenseID) {
		this.licenseID = licenseID;
	}

}
