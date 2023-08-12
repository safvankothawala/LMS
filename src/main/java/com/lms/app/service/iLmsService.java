package com.lms.app.service;

import com.lms.app.dto.CustomerResponse;
import com.lms.app.dto.DrawResponse;
import com.lms.app.dto.DrawWinnerResponse;
import com.lms.app.dto.GenerateTicketsResponse;
import com.lms.app.dto.GetActiveDrawsResponse;
import com.lms.app.dto.LicenseResponse;
import com.lms.app.dto.PurchaseTicketResponse;
import com.lms.app.dto.TicketOwnerResponse;
import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.TicketOwner;

/**
 * Interface for LMS Service
 */
public interface iLmsService {

	LicenseResponse createLicense(License license);

	CustomerResponse createCustomer(Customer customer);

	DrawResponse createDraw(Draw draw);

	TicketOwnerResponse createTicketOwner(TicketOwner ticketOwner);

	PurchaseTicketResponse purchaseTicket(String ticketNumber, String ticketOwnerIdentity);

	DrawWinnerResponse selectWinnerForDraw(Draw draw);

	GenerateTicketsResponse generateTicketsforDrawNumber(String drawNumber);
	
	GetActiveDrawsResponse getActiveDrawList();
}
