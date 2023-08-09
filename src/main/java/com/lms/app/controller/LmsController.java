/**
 * 
 */
package com.lms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.app.entity.Customer;
import com.lms.app.entity.Draw;
import com.lms.app.entity.License;
import com.lms.app.entity.Ticket;
import com.lms.app.entity.TicketAssociation;
import com.lms.app.entity.TicketOwner;
import com.lms.app.service.LmsServiceImpl;

/**
 * REST Controller for LMS
 */
@RestController
@RequestMapping("/lms")
public class LmsController {

	@Autowired
	private LmsServiceImpl lmsService;

	@GetMapping("/license/get/{id}")
	public License getLicenseforID(@PathVariable(required = true) Long id) {
		return lmsService.getLicenseForID(id);
	}

	@PostMapping("/license/create")
	public License createLicense(@RequestBody License license) {
		return lmsService.createLicense(license);
	}

	@GetMapping("/customer/get/{customerName}")
	public Customer getCustomerByName(@PathVariable(required = true) String customerName) {
		return lmsService.getCustomerByName(customerName);
	}

	@PostMapping("/customer/create")
	public Customer createCustomer(@RequestBody Customer customer) {
		return lmsService.createCustomer(customer);
	}

	@GetMapping("/draw/get/{drawNumber}")
	public Draw getDrawByDrawNumber(@PathVariable(required = true) String drawNumber) {
		return lmsService.getDrawByDrawNumber(drawNumber);
	}

	@PostMapping("/draw/create")
	public Draw createDraw(@RequestBody Draw draw) {
		return lmsService.createDraw(draw);
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

	@PostMapping("/ticketowner/create")
	public TicketOwner createTicket(@RequestBody TicketOwner ticketOwner) {
		return lmsService.createTicketOwner(ticketOwner);
	}

	@PostMapping("/ticketassociation/assign")
	public TicketAssociation assignTicketToTicketOwner(@RequestParam(name = "ticketNumber") String ticketNumber,
			@RequestParam(name = "ticketOwnerIdentity") String ticketOwnerIdentity) {
		return lmsService.assignTicketToTicketOwner(ticketNumber, ticketOwnerIdentity);
	}
	
	@PostMapping("/ticketassociation/setwinner/{ticketNumber}")
	public TicketAssociation setWinnerTicket(String ticketNumber) {
		return lmsService.setWinnerTicket(ticketNumber);
	}
}
