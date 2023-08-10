package com.lms.app.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

	/**
	 * Service for creating a License
	 */
	@Override
	public LicenseResponse createLicense(License license) {

		LicenseResponse licenseResponse = new LicenseResponse();
		try {
			// create License
			license = licenseRepository.save(license);
			licenseResponse.setLicenseID(license.getLicenseID());
		} catch (Exception e) {
			licenseResponse.setResponseCode(-1);
			licenseResponse.setResponseMessage("Error : " + e.getMessage());
		}

		return licenseResponse;
	}

	@Override
	public Customer getCustomerByCustomerIdentity(String customerIdentity) {
		return customerRepository.findByCustomerIdentity(customerIdentity);
	}

	/**
	 * Service for creating a Customer
	 */
	@Override
	public CustomerResponse createCustomer(Customer customer) {

		CustomerResponse customerResponse = new CustomerResponse();
		try {

			// Get License by LicenseKey
			License license = licenseRepository.findByLicenseKey(customer.getLicense().getLicenseKey());
			if (license != null) {
				customer.setLicense(license);
			} else {
				// License Key not found
				customerResponse.setResponseCode(-1);
				customerResponse.setResponseMessage("License Key not valid");
				return customerResponse;
			}

			// create Customer
			customer = customerRepository.save(customer);
			customerResponse.setCustomerID(customer.getCustomerID());
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				customerResponse.setResponseMessage("Cannot reuse the same license key for multiple customers");
			} else {
				customerResponse.setResponseMessage("Error : " + e.getMessage());
			}
			customerResponse.setResponseCode(-1);

		}

		return customerResponse;

	}

	/**
	 * Service for creating a Draw
	 */
	@Override
	public DrawResponse createDraw(Draw draw) {

		DrawResponse drawResponse = new DrawResponse();
		try {
			// create Draw
			draw = drawRepository.save(draw);
			drawResponse.setDrawID(draw.getDrawID());
		} catch (Exception e) {
			drawResponse.setResponseCode(-1);
			drawResponse.setResponseMessage("Error : " + e.getMessage());
		}

		return drawResponse;
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

	/**
	 * Service for creating a TicketOwner
	 */
	@Override
	public TicketOwnerResponse createTicketOwner(TicketOwner ticketOwner) {

		TicketOwnerResponse ticketOwnerResponse = new TicketOwnerResponse();
		try {

			// Get Customer by CustomerIdentity
			Customer customer = customerRepository
					.findByCustomerIdentity(ticketOwner.getCustomer().getCustomerIdentity());
			if (customer != null) {
				// Update Customer in TicketOwner
				ticketOwner.setCustomer(customer);
			} else {
				// Customer not found
				ticketOwnerResponse.setResponseCode(-1);
				ticketOwnerResponse.setResponseMessage(
						"Customer with Identity " + ticketOwner.getCustomer().getCustomerIdentity() + " not found");
				return ticketOwnerResponse;
			}

			// create TicketOwner
			ticketOwner = ticketOwnerRepository.save(ticketOwner);
			ticketOwnerResponse.setTicketOwnerID(ticketOwner.getTicketOwnerID());
		} catch (Exception e) {
			ticketOwnerResponse.setResponseCode(-1);

			if (e instanceof DataIntegrityViolationException) {
				ticketOwnerResponse.setResponseMessage("TicketOwner with same Identity already exists");
			} else {
				ticketOwnerResponse.setResponseMessage("Error : " + e.getMessage());
			}
		}

		return ticketOwnerResponse;

	}

	@Override
	public TicketOwner getTicketOwnerByTicketOwnerIdentity(String ticketOwnerIdentity) {
		return ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity);
	}

	/**
	 * Service for Purchase Ticket
	 */
	@Override
	public PurchaseTicketResponse purchaseTicket(String drawNumber, String ticketOwnerIdentity) {

		PurchaseTicketResponse purchaseTicketResponse = new PurchaseTicketResponse();
		try {
			TicketAssociation ticketAssociation = new TicketAssociation();

			Timestamp currentTimestamp = Timestamp.from(Instant.now());

			// Get Available Ticket for Draw
			Draw draw = drawRepository.findByDrawNumber(drawNumber);

			if (draw != null) {

				// Check for Draw Status
				if (currentTimestamp.after(draw.getEndDate()) || currentTimestamp.before(draw.getStartDate())) {

					// Draw is Over
					purchaseTicketResponse.setResponseCode(-1);
					purchaseTicketResponse.setResponseMessage("Ticket cannot be purchased as the Draw "
							+ draw.getDrawNumber() + " is not started or over");
					return purchaseTicketResponse;
				}

				// Get Ticket for Ticket Number
				List<Ticket> tickets = ticketRepository.findByDrawAndAvailable(draw, true);
				if (tickets != null && tickets.size() > 0) {

					// Update Ticket in Ticket Association
					ticketAssociation.setTicket(tickets.get(0));

				} else {
					// Available Tickets not found
					purchaseTicketResponse.setResponseCode(-1);
					purchaseTicketResponse.setResponseMessage("Tickets not available for Draw: " + drawNumber);
					return purchaseTicketResponse;
				}
			} else {
				// Draw not found
				purchaseTicketResponse.setResponseCode(-1);
				purchaseTicketResponse.setResponseMessage("Draw not found for draw number: " + drawNumber);
				return purchaseTicketResponse;
			}

			// Get Ticket Owner for Ticket Owner Identity
			TicketOwner ticketOwner = ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity);

			if (ticketOwner != null) {

				// Check for validity of Customer License
				if (currentTimestamp.after(ticketOwner.getCustomer().getLicense().getValidityPeriod())) {
					// Customer License is expired
					purchaseTicketResponse.setResponseCode(-1);
					purchaseTicketResponse.setResponseMessage("Ticket Owner belongs to a Customer "
							+ ticketOwner.getCustomer().getCustomerIdentity() + " whose license is expired");
					return purchaseTicketResponse;
				}

				// Check for Sold Tickets is not more than Customer License
				if (ticketOwner.getCustomer().getTickets().size() >= ticketOwner.getCustomer().getLicense()
						.getMaxTickets()) {
					// Max Tickets are sold for Customer
					purchaseTicketResponse.setResponseCode(-1);
					purchaseTicketResponse.setResponseMessage("Ticket Owner belongs to a Customer "
							+ ticketOwner.getCustomer().getCustomerIdentity() + " whose max tickets are already sold");
					return purchaseTicketResponse;
				}

				// Update TicketOwner and Customer in Ticket Association
				ticketAssociation.setTicketOwner(ticketOwner);
				ticketAssociation.setCustomer(ticketOwner.getCustomer());
			} else {
				// TicketOwner not found
				purchaseTicketResponse.setResponseCode(-1);
				purchaseTicketResponse
						.setResponseMessage("Ticket Owner Not Found with Identity " + ticketOwnerIdentity);
				return purchaseTicketResponse;
			}

			ticketAssociation.setWinner(false);

			// Associate Ticket to TicketOwner
			ticketAssociation = ticketAssociationRepository.save(ticketAssociation);
			purchaseTicketResponse.setTicketNumber(ticketAssociation.getTicket().getTicketNumber());
			purchaseTicketResponse.setTicketAssociationID(ticketAssociation.getTicketAssociationID());

			// Mark ticket as used
			ticketAssociation.getTicket().setAvailable(false);
			ticketRepository.save(ticketAssociation.getTicket());
		} catch (

		Exception e) {
			purchaseTicketResponse.setResponseCode(-1);
			purchaseTicketResponse.setResponseMessage("Error : " + e.getMessage());
		}
		return purchaseTicketResponse;
	}

	/**
	 * Service for Selection Winner for Draw
	 */
	@Override
	public DrawWinnerResponse selectWinnerForDraw(Draw draw) {

		DrawWinnerResponse drawWinnerResponse = new DrawWinnerResponse();

		try {

			draw = drawRepository.findByDrawNumber(draw.getDrawNumber());
			if (draw != null) {
				List<Ticket> tickets = ticketRepository.findByDrawAndAvailable(draw, false);
				if (!tickets.isEmpty()) {

					// Select Random Ticket as Winner Ticket
					int randomIndex = getRandomValue(0, tickets.size());
					Ticket winnerTicket = tickets.get(randomIndex);

					// Update Draw Winner Response
					TicketAssociation ticketAssociation = ticketAssociationRepository.findByTicket(winnerTicket);
					drawWinnerResponse.setCustomerIdentity(ticketAssociation.getCustomer().getCustomerIdentity());
					drawWinnerResponse.setTicketAssociationID(ticketAssociation.getTicketAssociationID());
					drawWinnerResponse.setTicketNumber(winnerTicket.getTicketNumber());
					drawWinnerResponse
							.setTicketOwnerIdentity(ticketAssociation.getTicketOwner().getTicketOwnerIdentity());

					// Update Ticket as Winner
					ticketAssociation.setWinner(true);
					ticketAssociationRepository.save(ticketAssociation);
				} else {
					// No Tickets available for Draw
					drawWinnerResponse.setResponseCode(-1);
					drawWinnerResponse.setResponseMessage("No Tickets available for Draw " + draw.getDrawNumber());
					return drawWinnerResponse;
				}

			} else {
				// Draw not found
				drawWinnerResponse.setResponseCode(-1);
				drawWinnerResponse.setResponseMessage("Draw not found");
				return drawWinnerResponse;

			}

		} catch (Exception e) {
			drawWinnerResponse.setResponseCode(-1);
			drawWinnerResponse.setResponseMessage(
					"Issue in selecting winner for Draw " + draw.getDrawNumber() + ". Please try again.");

		}

		return drawWinnerResponse;
	}

	private int getRandomValue(int Min, int Max) {

		// Get the random integer within Min and Max
		return ThreadLocalRandom.current().nextInt(Min, Max);
	}

	/**
	 * Service for generating Tickets for a Draw
	 */
	@Override
	public GenerateTicketsResponse generateTicketsforDrawNumber(String drawNumber) {

		GenerateTicketsResponse generateTicketsResponse = new GenerateTicketsResponse();
		try {

			// Get Draw for given Draw Number
			Draw draw = drawRepository.findByDrawNumber(drawNumber);

			long ticketCount = 1;

			// Check for existing Tickets
			List<Ticket> tickets = ticketRepository.findByDraw(draw);
			if (tickets.size() != 0 && tickets.size() <= draw.getMaxTickets()) {
				ticketCount = tickets.size() + 1;
			}

			// Generate Tickets for Max Tickets
			long ticketsGenerated = 0;
			while (ticketCount <= draw.getMaxTickets()) {
				Ticket ticket = new Ticket();
				ticket.setTicketNumber(drawNumber + "-" + ticketCount);
				ticket.setDraw(draw);
				ticketRepository.save(ticket);
				ticketCount++;
				ticketsGenerated++;
			}

			// Set Tickets Generated
			generateTicketsResponse.setTicketsGenerated(ticketsGenerated);

		} catch (Exception e) {
			generateTicketsResponse.setResponseCode(-1);
			generateTicketsResponse.setResponseMessage("Error : " + e.getMessage());
		}

		return generateTicketsResponse;
	}

}
