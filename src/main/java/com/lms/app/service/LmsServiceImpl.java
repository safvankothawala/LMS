/**
 * 
 */
package com.lms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketAssociation;
import com.lms.app.entity.TicketOwner;
import com.lms.app.repository.CustomerRepository;
import com.lms.app.repository.DrawRepository;
import com.lms.app.repository.LicenseRepository;
import com.lms.app.repository.TicketAssociationRepository;
import com.lms.app.repository.TicketOwnerRepository;
import com.lms.app.repository.TicketRepository;

/**
 * Service class for LMS
 */
@Service
public class LmsServiceImpl implements iLmsService {

	@Autowired
	private LicenseRepository licenseRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DrawRepository drawRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketOwnerRepository ticketOwnerRepository;

	@Autowired
	private TicketAssociationRepository ticketAssociationRepository;

	@Override
	public License getLicenseForID(long licenseID) {
		return licenseRepository.findById(licenseID).get();
	}

	@Override
	public License createLicense(License license) {
		return licenseRepository.save(license);
	}

	@Override
	public Customer getCustomerByName(String customerName) {
		return customerRepository.findByCustomerName(customerName);
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Draw createDraw(Draw draw) {
		return drawRepository.save(draw);
	}

	@Override
	public Draw getDrawByDrawNumber(String drawNumber) {
		return drawRepository.findByDrawNumber(drawNumber);
	}

	@Override
	public Ticket createTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket getTicketByTicketNumber(String ticketNumber) {
		return ticketRepository.findByTicketNumber(ticketNumber);
	}

	@Override
	public TicketOwner createTicketOwner(TicketOwner ticketOwner) {
		return ticketOwnerRepository.save(ticketOwner);
	}

	@Override
	public TicketOwner getTicketOwnerByTicketOwnerIdentity(String ticketOwnerIdentity) {
		return ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity);
	}

	@Override
	public TicketAssociation assignTicketToTicketOwner(String ticketNumber, String ticketOwnerIdentity) {
		TicketAssociation ticketAssociation = new TicketAssociation();

		Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
		ticketAssociation.setTicket(ticket);

		TicketOwner ticketOwner = ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity);
		ticketAssociation.setTicketOwner(ticketOwner);

		ticketAssociation.setWinner(false);

		return ticketAssociationRepository.save(ticketAssociation);
	}

	@Override
	public TicketAssociation setWinnerTicket(String ticketNumber) {

		TicketAssociation ticketAssociation = ticketAssociationRepository.getByTicketNumber(ticketNumber);
		ticketAssociation.setWinner(true);
		ticketAssociationRepository.save(ticketAssociation);
		return ticketAssociation;
	}

}
