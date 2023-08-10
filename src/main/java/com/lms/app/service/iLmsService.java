package com.lms.app.service;

import com.lms.app.dto.CustomerResponse;
import com.lms.app.dto.DrawResponse;
import com.lms.app.dto.DrawWinnerResponse;
import com.lms.app.dto.GenerateTicketsResponse;
import com.lms.app.dto.LicenseResponse;
import com.lms.app.dto.PurchaseTicketResponse;
import com.lms.app.dto.TicketOwnerResponse;
import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketOwner;

public interface iLmsService {

	License getLicenseForID(long licenseID);

	LicenseResponse createLicense(License license);

	Customer getCustomerByCustomerIdentity(String customerIdentity);

	CustomerResponse createCustomer(Customer customer);

	DrawResponse createDraw(Draw draw);

	Draw getDrawByDrawNumber(String drawNumber);

	Ticket createTicket(Ticket ticket);

	Ticket getTicketByTicketNumber(String ticketNumber);

	TicketOwnerResponse createTicketOwner(TicketOwner ticketOwner);

	TicketOwner getTicketOwnerByTicketOwnerIdentity(String ticketOwnerIdentity);

	PurchaseTicketResponse purchaseTicket(String ticketNumber, String ticketOwnerIdentity);

	DrawWinnerResponse selectWinnerForDraw(Draw draw);

	GenerateTicketsResponse generateTicketsforDrawNumber(String drawNumber);
}
