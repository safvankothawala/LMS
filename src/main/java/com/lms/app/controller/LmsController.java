/**
 * 
 */
package com.lms.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.app.dto.CustomerResponse;
import com.lms.app.dto.DrawResponse;
import com.lms.app.dto.DrawWinnerResponse;
import com.lms.app.dto.GenerateTicketsResponse;
import com.lms.app.dto.LicenseResponse;
import com.lms.app.dto.PurchaseTicketRequest;
import com.lms.app.dto.PurchaseTicketResponse;
import com.lms.app.dto.TicketOwnerResponse;
import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketOwner;
import com.lms.app.service.LmsServiceImpl;

/**
 * REST Controller for LMS
 */
@RestController
@RequestMapping("/lms")
public class LmsController {

	Logger logger = LogManager.getLogger(LmsController.class);

	@Autowired
	private LmsServiceImpl lmsService;

	@GetMapping("/license/get/{id}")
	public License getLicenseforID(@PathVariable(required = true) Long id) {
		return lmsService.getLicenseForID(id);
	}

	/**
	 * API to Create a License
	 * 
	 * @param LicenseKey, MaxTicketsPerDraw, ValidityPeriod
	 * @return ResponseCode, ResponseMessage and LicenseID
	 */
	@PostMapping("/license/create")
	public LicenseResponse createLicense(@RequestBody License license) {

		logger.debug("Create License with licenseKey: " + license.getLicenseKey() + " , maxTickets: "
				+ license.getMaxTickets() + " , ValidityPeriod: " + license.getValidityPeriod());

		// create License
		LicenseResponse licenseResponse = lmsService.createLicense(license);

		if (licenseResponse.getResponseCode() == 0) {
			logger.info("License " + license.getLicenseKey() + " created successfully with LicenseID: "
					+ licenseResponse.getLicenseID());
		} else {
			logger.error("Error in creating License with LicenseKey: " + license.getLicenseKey() + " , error: "
					+ licenseResponse.getResponseMessage());

		}

		return licenseResponse;

	}

	@GetMapping("/customer/get/{customerIdentity}")
	public Customer getCustomerByName(@PathVariable(required = true) String customerIdentity) {
		return lmsService.getCustomerByCustomerIdentity(customerIdentity);
	}

	/**
	 * API to create a Customer
	 * 
	 * @param customer
	 * @return
	 */
	@PostMapping("/customer/create")
	public CustomerResponse createCustomer(@RequestBody Customer customer) {

		logger.debug("Create Customer with CustomerIdentity: " + customer.getCustomerIdentity() + " , name: "
				+ customer.getCustomerName() + " , paymentMethod: " + customer.getPaymentMethod() + " , license: "
				+ customer.getLicense().getLicenseKey());

		// Create Customer
		CustomerResponse customerResponse = lmsService.createCustomer(customer);

		if (customerResponse.getResponseCode() == 0) {
			logger.info("Customer with Identity " + customer.getCustomerIdentity()
					+ " created successfully with CustomerID: " + customerResponse.getCustomerID());
		} else {
			logger.error("Error in creating Customer with CustomerIdentity: " + customer.getCustomerIdentity()
					+ " , error: " + customerResponse.getResponseMessage());

		}

		return customerResponse;

	}

	@GetMapping("/draw/get/{drawNumber}")
	public Draw getDrawByDrawNumber(@PathVariable(required = true) String drawNumber) {
		return lmsService.getDrawByDrawNumber(drawNumber);
	}

	/**
	 * API to create a Draw
	 * 
	 * @param DrawNumber, MaxTickets, StartDate and EndDate
	 * @return ResponseCode, ResponseMessage and DrawID
	 */
	@PostMapping("/draw/create")
	public DrawResponse createDraw(@RequestBody Draw draw) {
		logger.debug("Create Draw with drawNumber: " + draw.getDrawNumber() + " , maxTickets: " + draw.getMaxTickets()
				+ " , startDate: " + draw.getStartDate() + " , endDate: " + draw.getEndDate());

		// Create Draw
		DrawResponse drawResponse = lmsService.createDraw(draw);

		if (drawResponse.getResponseCode() == 0) {
			logger.info(
					"Draw " + draw.getDrawNumber() + " created successfully with DrawID: " + drawResponse.getDrawID());
		} else {
			logger.error("Error in creating Draw with DrawNumber: " + draw.getDrawNumber() + " , error: "
					+ drawResponse.getResponseMessage());

		}

		return drawResponse;
	}

	/**
	 * API to generate Tickets for a Draw
	 * 
	 * @param DrawNumber
	 * @return ResponseCode, ResponseMessage and NumberOfTickets
	 */
	@PostMapping("/draw/generateTickets")
	public GenerateTicketsResponse generateTicketsForDraw(@RequestBody Draw draw) {
		logger.debug("Generate Tickets for a Draw with drawNumber: " + draw.getDrawNumber());

		// Generate Tickets for a Draw
		GenerateTicketsResponse generateTicketsResponse = lmsService.generateTicketsforDrawNumber(draw.getDrawNumber());

		if (generateTicketsResponse.getResponseCode() == 0) {
			logger.info(generateTicketsResponse.getTicketsGenerated() + " tickets generated successfully for Draw: "
					+ draw.getDrawNumber());
		} else {
			logger.error("Error in generating Tickets for Draw with DrawNumber: " + draw.getDrawNumber() + " , error: "
					+ generateTicketsResponse.getResponseMessage());

		}

		return generateTicketsResponse;
	}

	@GetMapping("/ticket/get/{ticketNumber}")
	public Ticket getTicketByTicketNumber(@PathVariable(required = true) String ticketNumber) {
		return lmsService.getTicketByTicketNumber(ticketNumber);
	}

	@PostMapping("/ticket/create")
	public Ticket createTicket(@RequestBody Ticket ticket) {
		System.out.println(ticket);
		return lmsService.createTicket(ticket);
	}

	@GetMapping("/ticketowner/get/{ticketOwnerIdentity}")
	public TicketOwner getTicketOwnerByTicketOnwerIdentity(@PathVariable(required = true) String ticketOwnerIdentity) {
		return lmsService.getTicketOwnerByTicketOwnerIdentity(ticketOwnerIdentity);
	}

	/**
	 * API to create Ticket Owner
	 * 
	 * @param Name, MobileNumber, TicketOwnerIdentity, PaymentMethod,
	 *              CustomerIdentity
	 * @return ResponseCode, ResponseMessage and TicketOwnerID
	 */
	@PostMapping("/ticketowner/create")
	public TicketOwnerResponse createTicketOwner(@RequestBody TicketOwner ticketOwner) {

		logger.debug("Create TicketOwner with TicketOwnerIdentity: " + ticketOwner.getTicketOwnerIdentity()
				+ " , name: " + ticketOwner.getName() + " , paymentMethod: " + ticketOwner.getPaymentMethod()
				+ " , mobileNumber: " + ticketOwner.getMobileNumber() + " , customerIdentity: "
				+ ticketOwner.getCustomer().getCustomerIdentity());

		// Create Ticket Owner
		TicketOwnerResponse ticketOwnerResponse = lmsService.createTicketOwner(ticketOwner);

		if (ticketOwnerResponse.getResponseCode() == 0) {
			logger.info("TicketOwner with Identity " + ticketOwner.getTicketOwnerIdentity()
					+ " created successfully with TicketOwnerID: " + ticketOwnerResponse.getTicketOwnerID());
		} else {
			logger.error("Error in creating TicketOwner with TicketOwnerIdentity: "
					+ ticketOwner.getTicketOwnerIdentity() + " , error: " + ticketOwnerResponse.getResponseMessage());

		}

		return ticketOwnerResponse;

	}

	/**
	 * API to purchase a ticket. <br>
	 * The ticket associated with DrawNumber will be assigned to a Ticket Owner
	 * identified by Ticket Owner Identity.
	 * 
	 * @param drawNumber
	 * @param ticketOwnerIdentity
	 * @return ResponseCode, ResponseMessage, TicketNumber
	 */
	@PostMapping("/purchaseticket")
	public PurchaseTicketResponse assignTicketToTicketOwner(@RequestBody PurchaseTicketRequest purchaseTicketRequest) {

		logger.debug("Purchase a Ticket for Draw " + purchaseTicketRequest.getDrawNumber() + " for TicketOwner: "
				+ purchaseTicketRequest.getTicketOwnerIdentity());

		// Purchase Ticket
		PurchaseTicketResponse purchaseTicketResponse = lmsService.purchaseTicket(purchaseTicketRequest.getDrawNumber(),
				purchaseTicketRequest.getTicketOwnerIdentity());

		if (purchaseTicketResponse.getResponseCode() == 0) {
			logger.info("Purchase of Ticket " + purchaseTicketResponse.getTicketNumber() + " for Draw "
					+ purchaseTicketRequest.getDrawNumber() + " for TicketOwner "
					+ purchaseTicketRequest.getTicketOwnerIdentity() + " successful");
		} else {
			logger.error("Error in purchasing the Ticket for Draw: " + purchaseTicketRequest.getDrawNumber()
					+ " , error: " + purchaseTicketResponse.getResponseMessage());

		}

		return purchaseTicketResponse;

	}

	/**
	 * Select Winning Ticket for Draw
	 * 
	 * @param Draw Number
	 * @return DrawWinnerResponse
	 */
	@PostMapping("/draw/winner")
	public DrawWinnerResponse selectWinnerForDraw(@RequestBody Draw draw) {

		logger.debug("Select Winner for Draw " + draw.getDrawNumber());

		DrawWinnerResponse drawWinnerResponse = lmsService.selectWinnerForDraw(draw);

		if (drawWinnerResponse.getResponseCode() == 0) {
			logger.info("Winner Ticket Number: " + drawWinnerResponse.getTicketNumber() + " , Customer Identity: "
					+ drawWinnerResponse.getCustomerIdentity() + " and TicketOwner: "
					+ drawWinnerResponse.getTicketOwnerIdentity() + " for Draw Number: " + draw.getDrawNumber());
		} else {
			logger.error("Error in selecting Winner for Draw: " + draw.getDrawNumber() + " , error: "
					+ drawWinnerResponse.getResponseMessage());

		}

		return drawWinnerResponse;
	}
}
