package com.lms.app.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lms.app.dto.CustomerResponse;
import com.lms.app.dto.DrawResponse;
import com.lms.app.dto.DrawWinnerResponse;
import com.lms.app.dto.GenerateTicketsResponse;
import com.lms.app.dto.GetActiveDrawsResponse;
import com.lms.app.dto.LicenseResponse;
import com.lms.app.dto.PurchaseTicketResponse;
import com.lms.app.dto.TicketOwnerResponse;
import com.lms.app.dto.WinningTicket;
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
 * Implementation for LMS Service
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

	Logger logger = LogManager.getLogger(LmsServiceImpl.class);

	/**
	 * Service for creating a License
	 */
	@Override
	public LicenseResponse createLicense(License license) {

		logger.debug("Create License for LicenseKey: " + license.getLicenseKey() + " , Max Tickets: "
				+ license.getMaxTickets() + " , Validity Period: " + license.getValidityPeriod());

		LicenseResponse licenseResponse = new LicenseResponse();
		try {
			// create License
			license = licenseRepository.save(license);
			licenseResponse.setLicenseID(license.getLicenseID());

			logger.info("License created successfully. LicenseID: " + licenseResponse.getLicenseID());
		} catch (Exception e) {
			licenseResponse.setResponseCode(-1);
			licenseResponse.setResponseMessage("Error : " + e.getMessage());

			logger.error("Error in creating License: " + e.getMessage(), e);
		}

		return licenseResponse;
	}

	/**
	 * Service for creating a Customer
	 */
	@Override
	public CustomerResponse createCustomer(Customer customer) {

		logger.debug("Create Customer with Customer Identity: " + customer.getCustomerIdentity() + " , Customer Name: "
				+ customer.getCustomerName() + " , Payment Method: " + customer.getPaymentMethod() + " , LicenseKey: "
				+ customer.getLicense().getLicenseKey());

		CustomerResponse customerResponse = new CustomerResponse();
		try {

			// Get License by LicenseKey
			License license = licenseRepository.findByLicenseKey(customer.getLicense().getLicenseKey());
			if (license != null) {
				customer.setLicense(license);

				logger.debug("License found for key: " + customer.getLicense().getLicenseKey());

			} else {
				// License Key not found
				customerResponse.setResponseCode(-1);
				String errorMessage = "License Key " + customer.getLicense().getLicenseKey() + " is not valid";
				customerResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
				return customerResponse;
			}

			// check for uniqueness of Customer Identity
			Customer customerWithSameIdentity = customerRepository
					.findByCustomerIdentity(customer.getCustomerIdentity());
			if (customerWithSameIdentity != null) {
				customerResponse.setResponseCode(-1);
				String errorMessage = "Customer with same Identity " + customer.getCustomerIdentity()
						+ " already exists.";
				customerResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
				return customerResponse;
			}

			// create Customer
			customer = customerRepository.save(customer);
			customerResponse.setCustomerID(customer.getCustomerID());

			logger.info("Customer created successfully. CustomerID: " + customerResponse.getCustomerID());

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				customerResponse.setResponseMessage("License Key already in use.");
				logger.error("Cannot reuse the same license key for multiple customers: " + e.getMessage(), e);
			} else {
				customerResponse.setResponseMessage("Error : " + e.getMessage());
				logger.error("Error in creating Customer: " + e.getMessage(), e);
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

		logger.debug("Create Draw with Draw Number: " + draw.getDrawNumber() + " , Max Tickets: " + draw.getMaxTickets()
				+ " , Start Date: " + draw.getStartDate() + " , End Date: " + draw.getEndDate());

		DrawResponse drawResponse = new DrawResponse();
		try {
			// create Draw
			draw = drawRepository.save(draw);
			drawResponse.setDrawID(draw.getDrawID());

			logger.info("Draw created successfully. DrawID: " + drawResponse.getDrawID());

		} catch (Exception e) {
			drawResponse.setResponseCode(-1);
			drawResponse.setResponseMessage("Error : " + e.getMessage());

			logger.error("Error in creating Draw: " + e.getMessage(), e);
		}

		return drawResponse;
	}

	/**
	 * Service for creating a TicketOwner
	 */
	@Override
	public TicketOwnerResponse createTicketOwner(TicketOwner ticketOwner) {

		logger.debug("Create Ticket Owner with TicketOwner Identity: " + ticketOwner.getTicketOwnerIdentity()
				+ " , Customer Name: " + ticketOwner.getCustomer().getCustomerName() + " , Name: "
				+ ticketOwner.getName() + " , Mobile Number: " + ticketOwner.getMobileNumber() + " , Payment Method: "
				+ ticketOwner.getPaymentMethod());

		TicketOwnerResponse ticketOwnerResponse = new TicketOwnerResponse();
		try {
			
			if(ticketOwner.getTicketOwnerIdentity() == null | ticketOwner.getTicketOwnerIdentity().equals("")) {

				// Ticket Owner Identity is not available
				ticketOwnerResponse.setResponseCode(-1);
				String errorMessage = "Ticket Owner Identity missing in the request";
				ticketOwnerResponse.setResponseMessage(errorMessage);

				logger.error(errorMessage);

				return ticketOwnerResponse;
			
			}

			// Get Customer by CustomerIdentity
			Customer customer = customerRepository
					.findByCustomerIdentity(ticketOwner.getCustomer().getCustomerIdentity());
			if (customer != null) {
				// Update Customer in TicketOwner
				ticketOwner.setCustomer(customer);
			} else {
				// Customer not found
				ticketOwnerResponse.setResponseCode(-1);
				String errorMessage = "Customer with Identity " + ticketOwner.getCustomer().getCustomerIdentity()
						+ " not found";
				ticketOwnerResponse.setResponseMessage(errorMessage);

				logger.error(errorMessage);

				return ticketOwnerResponse;
			}

			// create TicketOwner
			ticketOwner = ticketOwnerRepository.save(ticketOwner);
			ticketOwnerResponse.setTicketOwnerID(ticketOwner.getTicketOwnerID());

			logger.info("Ticket Onwer created successfully. TicketOwnerID: " + ticketOwnerResponse.getTicketOwnerID());

		} catch (Exception e) {
			ticketOwnerResponse.setResponseCode(-1);

			if (e instanceof DataIntegrityViolationException) {
				ticketOwnerResponse.setResponseMessage("TicketOwner with same Identity already exists");
				logger.error("TicketOwner with same Identity already exists: " + e.getMessage(), e);
			} else {
				ticketOwnerResponse.setResponseMessage("Error : " + e.getMessage());
				logger.error("Error in creating Ticket Owner: " + e.getMessage(), e);
			}
		}

		return ticketOwnerResponse;

	}

	/**
	 * Service for Purchase Ticket
	 */
	@Override
	public PurchaseTicketResponse purchaseTicket(String drawNumber, String ticketOwnerIdentity) {

		logger.info(
				"Request for purchase of Ticket for Draw: " + drawNumber + " , Ticket Owner: " + ticketOwnerIdentity);

		PurchaseTicketResponse purchaseTicketResponse = new PurchaseTicketResponse();
		try {
			TicketAssociation ticketAssociation = new TicketAssociation();

			Timestamp currentTimestamp = Timestamp.from(Instant.now());

			// Get Available Ticket for Draw
			Draw draw = drawRepository.findByDrawNumber(drawNumber);

			if (draw != null) {

				logger.debug("Draw found for Draw Number: " + drawNumber);

				// Check for Draw Status
				if (currentTimestamp.after(draw.getEndDate()) || currentTimestamp.before(draw.getStartDate())) {

					// Draw is Over
					purchaseTicketResponse.setResponseCode(-1);
					String errorMessage = "Ticket cannot be purchased as the Draw " + draw.getDrawNumber()
							+ " is not started or over";
					purchaseTicketResponse.setResponseMessage(errorMessage);
					logger.error(errorMessage);
					return purchaseTicketResponse;
				}

				// Get Ticket for Ticket Number
				List<Ticket> tickets = ticketRepository.findByDrawAndAvailable(draw, true);
				if (tickets != null && tickets.size() > 0) {

					// Update Ticket in Ticket Association
					ticketAssociation.setTicket(tickets.get(0));

					logger.info("Ticket Selected for Purchase: " + ticketAssociation.getTicket().getTicketNumber()
							+ " for Draw: " + drawNumber + " and Ticket Owner: " + ticketOwnerIdentity);

				} else {
					// Available Tickets not found
					purchaseTicketResponse.setResponseCode(-1);
					String errorMessage = "Tickets not available for Draw: " + drawNumber;
					purchaseTicketResponse.setResponseMessage(errorMessage);
					logger.error(errorMessage);
					return purchaseTicketResponse;
				}
			} else {
				// Draw not found
				purchaseTicketResponse.setResponseCode(-1);
				String errorMessage = "Draw not found for draw number: " + drawNumber;
				purchaseTicketResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
				return purchaseTicketResponse;
			}

			// Get Ticket Owner for Ticket Owner Identity
			TicketOwner ticketOwner = ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity);

			if (ticketOwner != null) {

				// Check for validity of Customer License
				if (currentTimestamp.after(ticketOwner.getCustomer().getLicense().getValidityPeriod())) {
					// Customer License is expired
					purchaseTicketResponse.setResponseCode(-1);
					String errorMessage = "Ticket Owner belongs to a Customer "
							+ ticketOwner.getCustomer().getCustomerIdentity() + " whose license is expired";
					purchaseTicketResponse.setResponseMessage(errorMessage);
					logger.error(errorMessage);
					return purchaseTicketResponse;
				}

				// Check for Sold Tickets is not more than Customer License
				if (ticketOwner.getCustomer().getTickets().size() >= ticketOwner.getCustomer().getLicense()
						.getMaxTickets()) {
					// Max Tickets are sold for Customer
					purchaseTicketResponse.setResponseCode(-1);
					String errorMessage = "Max tickets for Customer " + ticketOwner.getCustomer().getCustomerIdentity()
							+ " sold out.";
					purchaseTicketResponse.setResponseMessage(errorMessage);
					logger.error(errorMessage);
					return purchaseTicketResponse;
				}

				// Update TicketOwner and Customer in Ticket Association
				ticketAssociation.setTicketOwner(ticketOwner);
				ticketAssociation.setCustomer(ticketOwner.getCustomer());

				logger.debug("Ticket Owner found for Ticket Owner Identity: " + ticketOwnerIdentity);

			} else {
				// TicketOwner not found
				purchaseTicketResponse.setResponseCode(-1);
				String errorMessage = "Ticket Owner Not Found with Identity " + ticketOwnerIdentity;
				purchaseTicketResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
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

			logger.info("Ticket " + ticketAssociation.getTicket().getTicketNumber()
					+ " purchase successfully for Ticket Owner: " + ticketOwnerIdentity);

		} catch (

		Exception e) {
			purchaseTicketResponse.setResponseCode(-1);
			purchaseTicketResponse.setResponseMessage("Error : " + e.getMessage());
			logger.error("Error : " + e.getMessage(), e);
		}
		return purchaseTicketResponse;
	}

	/**
	 * Service for Selection Winner for Draw
	 */
	@Override
	public DrawWinnerResponse selectWinnerForDraw(Draw draw) {

		logger.info("Select Winner for Draw: " + draw.getDrawNumber());

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

					logger.info("Lottery Winner: " + ticketAssociation.getTicket().getTicketNumber()
							+ " , Ticket Owner: " + ticketAssociation.getTicketOwner().getTicketOwnerIdentity()
							+ " , Customer: " + ticketAssociation.getCustomer().getCustomerIdentity());

					// Verify Identity of Ticket Owner
					verifyIdentityOfTicketOwner(ticketAssociation.getTicketOwner());

					// Pay Lottery prize to Customer & Ticket Owner
					doPaymentToCustomerAndTicketOwner(ticketAssociation);

				} else {
					// No Tickets available for Draw
					drawWinnerResponse.setResponseCode(-1);
					String errorMessage = "No Tickets available for Draw " + draw.getDrawNumber();
					drawWinnerResponse.setResponseMessage(errorMessage);
					logger.error(errorMessage);
					return drawWinnerResponse;
				}

			} else {
				// Draw not found
				drawWinnerResponse.setResponseCode(-1);
				String errorMessage = "Draw not found";
				drawWinnerResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
				return drawWinnerResponse;

			}

		} catch (Exception e) {
			drawWinnerResponse.setResponseCode(-1);
			drawWinnerResponse.setResponseMessage(
					"Issue in selecting winner for Draw " + draw.getDrawNumber() + ". Please try again.");
			logger.error("Exception in selecting winner for Draw " + draw.getDrawNumber(), e);

		}

		return drawWinnerResponse;
	}

	/*
	 * Verify Identity of Ticket Owner
	 */
	private void verifyIdentityOfTicketOwner(TicketOwner ticketOwner) {

		// Call External Authentication System to validate the Identity of Ticket Owner
		logger.info("Ticket Owner identity " + ticketOwner.getTicketOwnerIdentity()
				+ " verification performed successfully");

	}

	/*
	 * Pay to Customer and Ticket Owner
	 */
	private void doPaymentToCustomerAndTicketOwner(TicketAssociation ticketAssociation) {

		/*
		 * Call Payment Gateway APIs to perform the payment as per the Payment Method
		 * associated with Customer
		 */
		Customer customer = ticketAssociation.getCustomer();
		logger.info("Payment done to Customer: " + customer.getCustomerIdentity() + " , PaymentMethod: "
				+ customer.getPaymentMethod());

		/*
		 * Call Payment Gateway APIs to perform the payment as per the Payment Method
		 * associated with Ticket Owner
		 */
		TicketOwner ticketOwner = ticketAssociation.getTicketOwner();
		logger.info("Payment done to Ticket Owner: " + ticketOwner.getTicketOwnerIdentity() + " , PaymentMethod: "
				+ ticketOwner.getPaymentMethod());

	}

	/*
	 * Generate Random Value for picking up the Winner Ticket
	 */
	private int getRandomValue(int Min, int Max) {

		// Get the random integer within Min and Max
		return ThreadLocalRandom.current().nextInt(Min, Max);
	}

	/**
	 * Service for generating Tickets for a Draw
	 */
	@Override
	public GenerateTicketsResponse generateTicketsforDrawNumber(String drawNumber) {

		logger.debug("Generate Tickets for Draw: " + drawNumber);

		GenerateTicketsResponse generateTicketsResponse = new GenerateTicketsResponse();
		try {

			// Get Draw for given Draw Number
			Draw draw = drawRepository.findByDrawNumber(drawNumber);

			if (draw != null) {
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

				logger.info(ticketsGenerated + " tickets generated for Draw: " + drawNumber);

			} else {
				generateTicketsResponse.setResponseCode(-1);
				String errorMessage = "Draw not found for Draw Number: " + drawNumber;
				generateTicketsResponse.setResponseMessage(errorMessage);
				logger.error(errorMessage);
			}

		} catch (Exception e) {
			generateTicketsResponse.setResponseCode(-1);
			generateTicketsResponse.setResponseMessage("Error : " + e.getMessage());
			logger.error("Exception in generating Tickets for Draw " + drawNumber, e);
		}

		return generateTicketsResponse;
	}

	/**
	 * Service for getting Active Draws
	 */
	public GetActiveDrawsResponse getActiveDrawList() {

		// Find all Draws
		List<Draw> draws = drawRepository.findAll();

		// Include only those Draws which are Active
		Date today = Calendar.getInstance().getTime();
		List<Draw> activeDraws = draws.stream().filter(p -> p.getEndDate().after(today))
				.filter(p -> p.getStartDate().before(today)).collect(Collectors.toList());
		GetActiveDrawsResponse getActiveDrawsResponse = new GetActiveDrawsResponse();
		getActiveDrawsResponse.setDraws(activeDraws);
		return getActiveDrawsResponse;

	}

	public WinningTicket checkForWinningTicketForCustomer(String customerIdentity) {

		if (customerIdentity != null) {

			List<TicketAssociation> winnerTickets = ticketAssociationRepository.findByWinner(true);
			List<TicketAssociation> customerWinningTickets = new ArrayList<TicketAssociation>();

			for (TicketAssociation ticketAssociation : winnerTickets) {
				if (ticketAssociation.getCustomer().getCustomerIdentity().equals(customerIdentity)) {
					customerWinningTickets.add(ticketAssociation);
				}
			}

			if (!customerWinningTickets.isEmpty()) {
				customerWinningTickets.sort((o1, o2) -> o1.getCustomer().getLastModifiedDate()
						.compareTo(o2.getCustomer().getLastModifiedDate()));
				TicketAssociation winningTicketForCustomer = customerWinningTickets.get(0);
				WinningTicket winningTicket = new WinningTicket();
				winningTicket.setTicketNumber(winningTicketForCustomer.getTicket().getTicketNumber());
				winningTicket
						.setTicketOwnerIdentity(winningTicketForCustomer.getTicketOwner().getTicketOwnerIdentity());
				winningTicket.setDrawNumber(winningTicketForCustomer.getTicket().getDraw().getDrawNumber());
				return winningTicket;
			}
		}

		return null;
	}

	/*
	 * @Override public Customer getCustomerByCustomerIdentity(String
	 * customerIdentity) { return
	 * customerRepository.findByCustomerIdentity(customerIdentity); }
	 */

	/*
	 * @Override public Draw getDrawByDrawNumber(String drawNumber) { return
	 * drawRepository.findByDrawNumber(drawNumber); }
	 * 
	 * @Override public Ticket createTicket(Ticket ticket) { return
	 * ticketRepository.save(ticket); }
	 * 
	 * @Override public Ticket getTicketByTicketNumber(String ticketNumber) { return
	 * ticketRepository.findByTicketNumber(ticketNumber); }
	 */

	/*
	 * @Override public TicketOwner getTicketOwnerByTicketOwnerIdentity(String
	 * ticketOwnerIdentity) { return
	 * ticketOwnerRepository.findByTicketOwnerIdentity(ticketOwnerIdentity); }
	 */

	/*
	 * @Override public License getLicenseForID(long licenseID) { return
	 * licenseRepository.findById(licenseID).get(); }
	 */
}
