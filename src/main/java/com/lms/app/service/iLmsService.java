package com.lms.app.service;

import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketAssociation;
import com.lms.app.entity.TicketOwner;

public interface iLmsService {

	License getLicenseForID(long licenseID);

	License createLicense(License license);

	Customer getCustomerByName(String customerName);

	Customer createCustomer(Customer customer);

	Draw createDraw(Draw draw);

	Draw getDrawByDrawNumber(String drawNumber);

	Ticket createTicket(Ticket ticket);

	Ticket getTicketByTicketNumber(String ticketNumber);

	TicketOwner createTicketOwner(TicketOwner ticketOwner);

	TicketOwner getTicketOwnerByTicketOwnerIdentity(String ticketOwnerIdentity);

	TicketAssociation assignTicketToTicketOwner(String ticketNumber, String ticketOwnerIdentity);

	TicketAssociation setWinnerTicket(String ticketNumber);
}
